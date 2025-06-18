package com.van.imadownload;

import com.van.imadownload.dto.ShareInfoResponse;
import com.van.imadownload.dto.helper.FailedDownload;
import com.van.imadownload.dto.helper.FolderInfo;
import com.van.imadownload.dto.helper.KnowledgeItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SyncManager {
    private final ImaApiService apiService;
    private final FileDownloader downloader;
    private final String shareId;
    private final ExecutorService executor;

    // 使用线程安全的列表来收集所有层级的失败任务
    private final List<FailedDownload> allFailedDownloads = Collections.synchronizedList(new ArrayList<>());

    public SyncManager(ImaApiService apiService, FileDownloader downloader, String shareId, ExecutorService executor) {
        this.apiService = apiService;
        this.downloader = downloader;
        this.shareId = shareId;
        this.executor = executor;
    }

    /**
     * 启动整个同步和下载过程。
     */
    public void startSync() {
        System.out.println("--- Starting Synchronization Process ---");
        try {
            // 1. 发起第一次请求以获取根目录信息
            ShareInfoResponse initialResponse = apiService.getShareInfo(shareId, 1, "", "");
            if (initialResponse == null || initialResponse.getCode() != 0 || initialResponse.getCurrentPath().isEmpty()) {
                System.err.println("Failed to get initial folder info. Aborting.");
                return;
            }

            // 2. 创建根目录
            String rootDirName = sanitizeName(initialResponse.getCurrentPath().get(0).getName());
            Path rootPath = Paths.get("downloads", rootDirName);
            Files.createDirectories(rootPath);
            System.out.println("Root directory created at: " + rootPath.toAbsolutePath());

            // 3. 从根目录开始递归处理
            processFolder("", rootPath);

            // 4. 等待所有下载任务完成
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                System.err.println("Download tasks timed out after 1 hour. Forcing shutdown.");
                executor.shutdownNow();
            }

            // 5. 失败重试
            retryFailedDownloads();

        } catch (IOException | InterruptedException e) {
            System.err.println("A critical error occurred during the sync process.");
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 递归处理指定 folderId 的内容，并下载到 currentDirectory。
     */
    private void processFolder(String folderId, Path currentDirectory) throws IOException {
        System.out.println("\nProcessing folder: " + currentDirectory);
        List<KnowledgeItem> itemsToDownload = new ArrayList<>();
        List<KnowledgeItem> subfoldersToProcess = new ArrayList<>();

        // 分页获取当前文件夹下的所有条目
        String currentCursor = "";
        boolean isEnd;
        do {
            ShareInfoResponse response = apiService.getShareInfo(shareId, 20, currentCursor, folderId);
            if (response == null || response.getCode() != 0) {
                System.err.println("Failed to fetch content for folderId: " + folderId);
                return; // 跳过这个文件夹
            }

            // 将条目分类为文件或子文件夹
            for (KnowledgeItem item : response.getKnowledgeList()) {
                if (item.getFolderInfo() != null) {
                    subfoldersToProcess.add(item);
                } else {
                    itemsToDownload.add(item);
                }
            }
            isEnd = response.isEnd();
            currentCursor = response.getNextCursor();
        } while (!isEnd && currentCursor != null && !currentCursor.isEmpty());
        
        // 提交当前目录的文件下载任务
        if (!itemsToDownload.isEmpty()) {
            System.out.println("Found " + itemsToDownload.size() + " files to download in " + currentDirectory.getFileName());
            List<FailedDownload> failures = downloader.downloadFilesConcurrently(itemsToDownload, currentDirectory.toString(), executor);
            allFailedDownloads.addAll(failures);
        }

        // 递归处理子文件夹
        for (KnowledgeItem folderItem : subfoldersToProcess) {
            FolderInfo folderInfo = folderItem.getFolderInfo();
            String subDirName = sanitizeName(folderInfo.getName());
            Path subDirPath = currentDirectory.resolve(subDirName);
            Files.createDirectories(subDirPath);
            processFolder(folderInfo.getFolderId(), subDirPath);
        }
    }

    private void retryFailedDownloads() {
        if (allFailedDownloads.isEmpty()) {
            System.out.println("\n--- No initial failures. All downloads were successful. ---");
            return;
        }

        System.out.println("\n--- Phase: Retrying " + allFailedDownloads.size() + " Failed Downloads ---");
        List<FailedDownload> unrecoverable = new ArrayList<>();
        // 从失败列表中移除并尝试重试
        List<FailedDownload> toRetry = new ArrayList<>(allFailedDownloads);
        allFailedDownloads.clear();

        for (FailedDownload failed : toRetry) {
            try {
                System.out.println("[RETRYING] -> " + failed.getOutputPath().getFileName());
                downloader.downloadSingleFile(failed.getDownloadUrl(), failed.getOutputPath());
            } catch (IOException e) {
                System.err.println("[RETRY FAIL] Could not download: " + failed.getOutputPath().getFileName());
                unrecoverable.add(failed);
            }
        }
        allFailedDownloads.addAll(unrecoverable); // 更新最终的失败列表
    }

    public void printFinalReport() {
        System.out.println("\n--- Final Report ---");
        // 注意：由于是递归获取，无法简单地通过一次API调用拿到准确的总数。
        // 这里的报告将基于实际处理情况。
        System.out.println("Unrecoverable failures: " + allFailedDownloads.size());
        if (!allFailedDownloads.isEmpty()) {
            System.out.println("List of unrecoverable files:");
            allFailedDownloads.forEach(f -> System.out.println("  - " + f.getOutputPath()));
        }
        System.out.println("Synchronization process finished.");
    }
    
    private String sanitizeName(String name) {
        if (name == null || name.isEmpty()) {
            return "unnamed_folder";
        }
        return name.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }
}
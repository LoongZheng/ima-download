package com.van.imadownload;

import com.van.imadownload.dto.helper.FailedDownload;
import com.van.imadownload.dto.helper.KnowledgeItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class FileDownloader {

    private final OkHttpClient httpClient;

    public FileDownloader(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 并发下载文件列表。
     * @param items 要下载的知识条目列表
     * @param directoryPath 下载目录
     * @param executor 外部传入的线程池
     * @return 一个包含所有初次下载失败任务的列表
     */
    public List<FailedDownload> downloadFilesConcurrently(List<KnowledgeItem> items, String directoryPath, ExecutorService executor) {
        final List<FailedDownload> failedDownloads = Collections.synchronizedList(new ArrayList<>());

        Path downloadDir = Paths.get(directoryPath);
        try {
            Files.createDirectories(downloadDir);
        } catch (IOException e) {
            System.err.println("FATAL: Failed to create download directory: " + directoryPath);
            // 如果目录创建失败，所有任务都会失败，直接返回所有任务
            return items.stream()
                    .map(item -> new FailedDownload(extractDownloadUrlFrom(item.getJumpUrl()), downloadDir.resolve(sanitizeFilename(item.getTitle()))))
                    .collect(Collectors.toList());
        }

        for (final KnowledgeItem item : items) {
            executor.submit(() -> {
                String cleanDownloadUrl = extractDownloadUrlFrom(item.getJumpUrl());
                String sanitizedFileName = sanitizeFilename(item.getTitle());
                if (cleanDownloadUrl.isEmpty() || sanitizedFileName.isEmpty()) {
                    return; // 跳过无效条目
                }
                
                Path outputPath = downloadDir.resolve(sanitizedFileName);
                if (Files.exists(outputPath)) {
                    // System.out.println("File already exists, skipping: " + outputPath.getFileName());
                    return;
                }

                try {
                    downloadSingleFile(cleanDownloadUrl, outputPath);
                } catch (IOException e) {
                    System.err.println("[FAIL] Download failed for: " + sanitizedFileName + " | Reason: " + e.getMessage());
                    failedDownloads.add(new FailedDownload(cleanDownloadUrl, outputPath));
                }
            });
        }
        return failedDownloads;
    }

    /**
     * 下载单个文件。如果失败，会抛出IOException。
     * @param url 文件URL
     * @param outputPath 保存路径
     * @throws IOException 下载或文件写入失败时抛出
     */
    public void downloadSingleFile(String url, Path outputPath) throws IOException {
        System.out.println("[DOWNLOADING] -> " + outputPath.getFileName());
        Request request = new Request.Builder().url(url).build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Server responded with code: " + response.code());
            }

            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("Response body is null.");
            }
            
            try (InputStream in = body.byteStream();
                 OutputStream out = Files.newOutputStream(outputPath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    // --- Helper methods (sanitizeFilename, extractDownloadUrlFrom) remain the same ---

    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "unknown_file";
        }
        return filename.replaceAll("[\\\\/:*?\"<>|]", "_").trim();
    }
    
    private String extractDownloadUrlFrom(String jumpUrl) {
        if (jumpUrl == null || jumpUrl.isEmpty()) {
            return "";
        }
        String marker = "&imaei=";
        int markerIndex = jumpUrl.indexOf(marker);
        return (markerIndex != -1) ? jumpUrl.substring(0, markerIndex) : jumpUrl;
    }
}
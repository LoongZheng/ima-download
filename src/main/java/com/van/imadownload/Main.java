package com.van.imadownload;

import okhttp3.OkHttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String SHARE_ID = "7262497c4556f04445b54e8579295a8c65db4222306fae7d6193b926ba15d3e1";
    private static final int DOWNLOAD_THREADS = 20; // 并发下载线程数

    public static void main(String[] args) {
        // --- 1. 初始化 ---
        OkHttpClient sharedClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS) // 增加读取超时
                .build();
        
        ExecutorService executor = Executors.newFixedThreadPool(DOWNLOAD_THREADS);

        ImaApiService apiService = new ImaApiService(sharedClient);
        FileDownloader downloader = new FileDownloader(sharedClient);
        
        // --- 2. 创建并启动同步管理器 ---
        SyncManager syncManager = new SyncManager(apiService, downloader, SHARE_ID, executor);
        syncManager.startSync();

        // --- 3. 打印最终报告 ---
        syncManager.printFinalReport();
    }
}

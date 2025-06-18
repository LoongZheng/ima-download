package com.van.imadownload;

import okhttp3.OkHttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String SHARE_ID = "0cd7e311215e7aff35a7f5f2aec563ea800e5217ad1c6a87d8d3b70d33f39f68";
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
package com.van.imadownload.dto.helper;

import java.nio.file.Path;

/**
 * 用于存储下载失败任务信息的不可变数据类。
 */
public class FailedDownload {
    private final String downloadUrl;
    private final Path outputPath;

    public FailedDownload(String downloadUrl, Path outputPath) {
        this.downloadUrl = downloadUrl;
        this.outputPath = outputPath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    @Override
    public String toString() {
        return "FailedDownload{" +
                "url='" + downloadUrl + '\'' +
                ", path=" + outputPath +
                '}';
    }
}
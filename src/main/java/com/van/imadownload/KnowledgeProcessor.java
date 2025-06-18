package com.van.imadownload;

import com.van.imadownload.dto.helper.KnowledgeItem;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 知识条目处理器，用于从API响应中提取和处理数据。
 */
public class KnowledgeProcessor {

    /**
     * 从一个 jumpUrl 中提取下载链接。
     * 根据需求，链接在 "&imaei=" 参数之前截断。
     * @param jumpUrl 原始的跳转URL
     * @return 清理后的下载URL
     */
    private String extractDownloadUrlFrom(String jumpUrl) {
        if (jumpUrl == null || jumpUrl.isEmpty()) {
            return "";
        }
        // 查找 "&imaei=" 作为截断标记
        String marker = "&imaei=";
        int markerIndex = jumpUrl.indexOf(marker);

        if (markerIndex != -1) {
            // 如果找到了标记，截取它之前的部分
            return jumpUrl.substring(0, markerIndex);
        } else {
            // 如果没有找到标记，返回原始URL
            return jumpUrl;
        }
    }

    /**
     * 批量从知识条目列表中提取所有下载链接。
     * @param knowledgeList 从API获取的知识条目列表
     * @return 一个包含所有处理后下载链接的列表
     */
    public List<String> extractDownloadUrls(List<KnowledgeItem> knowledgeList) {
        // 防御性编程：处理null或空列表的情况
        if (knowledgeList == null || knowledgeList.isEmpty()) {
            return Collections.emptyList();
        }

        // 使用Java Stream API进行处理，代码更简洁
        return knowledgeList.stream()
                // 从每个KnowledgeItem对象中获取jumpUrl
                .map(KnowledgeItem::getJumpUrl)
                // 过滤掉null的url，防止空指针
                .filter(Objects::nonNull)
                // 对每个jumpUrl应用提取逻辑
                .map(this::extractDownloadUrlFrom)
                // 将处理后的结果收集到一个新的List中
                .collect(Collectors.toList());
    }
}
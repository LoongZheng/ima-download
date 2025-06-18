package com.van.imadownload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.imadownload.dto.ShareInfoRequest;
import com.van.imadownload.dto.ShareInfoResponse;
import okhttp3.*;

import java.io.IOException;

public class ImaApiService {

    private static final String API_URL = "https://ima.qq.com/cgi-bin/knowledge_share_get/get_share_info";
    // 定义JSON媒体类型
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ImaApiService(OkHttpClient httpClient) {
        // 最佳实践：创建单个OkHttpClient实例并复用
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 获取分享信息
     *
     * @param shareId  分享ID (必需)
     * @param limit    每页数量 (必需)
     * @param cursor   分页游标 (可选, 首页为空字符串)
     * @param folderId 文件夹ID (可选)
     * @return 解析后的ShareInfoResponse对象
     * @throws IOException 当网络请求或解析失败时抛出
     */
    public ShareInfoResponse getShareInfo(String shareId, int limit, String cursor, String folderId)
            throws IOException {

        // 1. 创建请求体对象
        ShareInfoRequest requestPayload = new ShareInfoRequest(shareId, cursor, limit, folderId);

        // 2. 将请求体对象序列化为JSON字符串
        String requestBodyJson = objectMapper.writeValueAsString(requestPayload);

        // 3. 使用OkHttp创建RequestBody
        RequestBody body = RequestBody.create(requestBodyJson, JSON);

        // 4. 构建OkHttp的Request对象
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        // 5. 发送请求并处理响应 (使用try-with-resources确保响应被关闭)
        try (Response response = httpClient.newCall(request).execute()) {
            // 6. 检查响应是否成功
            if (!response.isSuccessful()) {
                // 如果请求失败，抛出带有状态码和消息的异常
                throw new IOException("Unexpected response code: " + response);
            }

            // 7. 获取响应体并反序列化为Java对象
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IOException("Response body is null");
            }

            return objectMapper.readValue(responseBody.string(), ShareInfoResponse.class);
        }
    }
}
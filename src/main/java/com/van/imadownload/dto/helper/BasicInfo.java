package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BasicInfo {
    @JsonProperty("cover_audit_status")
    private int coverAuditStatus;
    @JsonProperty("cover_url")
    private String coverUrl;
    @JsonProperty("create_timestamp_sec")
    private String createTimestampSec;
    @JsonProperty("creator")
    private Creator creator;
    @JsonProperty("description")
    private String description;
    @JsonProperty("forbidden_info")
    private Object forbiddenInfo; // Use Object for unknown/nullable complex types
    @JsonProperty("guest_cover_cos_key")
    private String guestCoverCosKey;
    @JsonProperty("has_deleted")
    private boolean hasDeleted;
    @JsonProperty("knowledge_total_size")
    private String knowledgeTotalSize;
    @JsonProperty("name")
    private String name;
    @JsonProperty("recommended_questions")
    private List<Object> recommendedQuestions;
    @JsonProperty("session_by_keyword")
    private String sessionByKeyword;
    @JsonProperty("size")
    private String size;
    @JsonProperty("update_timestamp_sec")
    private String updateTimestampSec;
}

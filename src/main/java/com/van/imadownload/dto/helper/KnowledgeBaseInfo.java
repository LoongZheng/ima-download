package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeBaseInfo {
    @JsonProperty("basic_info")
    private BasicInfo basicInfo;
    @JsonProperty("comment_info")
    private CommentInfo commentInfo;
    @JsonProperty("id")
    private String id;
    @JsonProperty("member_info")
    private MemberInfo memberInfo;
    @JsonProperty("permission_info")
    private PermissionInfo permissionInfo;
    @JsonProperty("type")
    private int type;
    @JsonProperty("user_permission_info")
    private UserPermissionInfo userPermissionInfo;
}
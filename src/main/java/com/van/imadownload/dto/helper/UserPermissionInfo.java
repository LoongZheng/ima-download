package com.van.imadownload.dto.helper;// File: UserPermissionInfo.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserPermissionInfo {
    @JsonProperty("is_in_apply_list")
    private boolean isInApplyList;
    @JsonProperty("role_type")
    private int roleType;
}
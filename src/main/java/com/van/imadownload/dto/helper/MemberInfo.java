package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MemberInfo {
    @JsonProperty("apply_count")
    private int applyCount;
    @JsonProperty("member_count")
    private int memberCount;
}
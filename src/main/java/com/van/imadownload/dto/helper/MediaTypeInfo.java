package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MediaTypeInfo {
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tips")
    private String tips;
}
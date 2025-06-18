package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VersionMessage {
    @JsonProperty("support_version")
    private String supportVersion;
    @JsonProperty("tips")
    private String tips;
}
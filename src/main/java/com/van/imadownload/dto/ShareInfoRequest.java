package com.van.imadownload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShareInfoRequest {

    @JsonProperty("share_id")
    private String shareId;

    @JsonProperty("cursor")
    private String cursor;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("folder_id")
    private String folderId;
}
package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentInfo {
    @JsonProperty("comment_count")
    private String commentCount;
}
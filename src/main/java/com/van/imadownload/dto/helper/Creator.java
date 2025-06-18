package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Creator {
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("certification_info")
    private CertificationInfo certificationInfo;
    @JsonProperty("knowledge_matrix_id")
    private String knowledgeMatrixId;
    @JsonProperty("nickname")
    private String nickname;
}
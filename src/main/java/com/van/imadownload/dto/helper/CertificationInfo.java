package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CertificationInfo {
    @JsonProperty("company_certification_info")
    private Object companyCertificationInfo;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("personal_certification_info")
    private Object personalCertificationInfo;
    @JsonProperty("title")
    private String title;
    @JsonProperty("type")
    private int type;
    @JsonProperty("type_desc")
    private String typeDesc;
}
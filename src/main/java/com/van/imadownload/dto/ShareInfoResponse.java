package com.van.imadownload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.van.imadownload.dto.helper.CurrentPath;
import com.van.imadownload.dto.helper.KnowledgeBaseInfo;
import com.van.imadownload.dto.helper.KnowledgeItem;
import com.van.imadownload.dto.helper.VersionMessage;
import lombok.Data;

import java.util.List;

@Data
public class ShareInfoResponse {
    @JsonProperty("code")
    private int code;

    @JsonProperty("current_path")
    private List<CurrentPath> currentPath;

    @JsonProperty("initializing")
    private boolean initializing;

    @JsonProperty("is_end")
    private boolean isEnd;

    @JsonProperty("is_in_apply_list")
    private boolean isInApplyList;

    @JsonProperty("is_update")
    private boolean isUpdate;

    @JsonProperty("knowledge_base_info")
    private KnowledgeBaseInfo knowledgeBaseInfo;

    @JsonProperty("knowledge_list")
    private List<KnowledgeItem> knowledgeList;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("next_cursor")
    private String nextCursor;

    @JsonProperty("total_size")
    private String totalSize;

    @JsonProperty("version")
    private String version;

    @JsonProperty("version_message")
    private VersionMessage versionMessage;
}
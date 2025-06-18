package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class KnowledgeItem {
    @JsonProperty("abstract")
    private String itemAbstract; // "abstract" is a Java keyword
    @JsonProperty("access_status")
    private int accessStatus;
    @JsonProperty("access_status_update_ts")
    private String accessStatusUpdateTs;
    @JsonProperty("cover_urls")
    private List<String> coverUrls;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("file_size")
    private String fileSize;
    @JsonProperty("folder_info")
    private FolderInfo folderInfo;
    @JsonProperty("forbidden_info")
    private Object forbiddenInfo;
    @JsonProperty("highlight_tags")
    private List<Object> highlightTags;
    @JsonProperty("introduction")
    private String introduction;
    @JsonProperty("is_repeated")
    private boolean isRepeated;
    @JsonProperty("jump_url")
    private String jumpUrl;
    @JsonProperty("last_modify_time")
    private String lastModifyTime;
    @JsonProperty("last_open_time")
    private String lastOpenTime;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("md5_sum")
    private String md5Sum;
    @JsonProperty("media_audit_status")
    private int mediaAuditStatus;
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("media_state")
    private int mediaState;
    @JsonProperty("media_type")
    private int mediaType;
    @JsonProperty("media_type_info")
    private MediaTypeInfo mediaTypeInfo;
    @JsonProperty("parent_folder_id")
    private String parentFolderId;
    @JsonProperty("parse_err_info")
    private Object parseErrInfo;
    @JsonProperty("parse_progress")
    private int parseProgress;
    @JsonProperty("parsed_file_url")
    private String parsedFileUrl;
    @JsonProperty("password")
    private String password;
    @JsonProperty("raw_file_url")
    private String rawFileUrl;
    @JsonProperty("second_index")
    private String secondIndex;
    @JsonProperty("source_path")
    private String sourcePath;
    @JsonProperty("summary_state")
    private int summaryState;
    @JsonProperty("tags")
    private List<Object> tags;
    @JsonProperty("title")
    private String title;
    @JsonProperty("update_time")
    private String updateTime;
}
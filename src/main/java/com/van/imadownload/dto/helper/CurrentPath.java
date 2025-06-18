package com.van.imadownload.dto.helper;// File: CurrentPath.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentPath {
    @JsonProperty("file_number")
    private String fileNumber;
    @JsonProperty("folder_id")
    private String folderId;
    @JsonProperty("folder_number")
    private String folderNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent_folder_id")
    private String parentFolderId;
}
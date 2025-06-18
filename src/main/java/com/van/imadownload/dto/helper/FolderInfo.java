package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FolderInfo {

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
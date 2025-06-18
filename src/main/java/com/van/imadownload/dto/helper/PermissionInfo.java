package com.van.imadownload.dto.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PermissionInfo {
    @JsonProperty("access_status")
    private int accessStatus;
    @JsonProperty("forbid_member_access_content")
    private boolean forbidMemberAccessContent;
    @JsonProperty("requires_approval_for_join")
    private boolean requiresApprovalForJoin;
    @JsonProperty("visible_export_status")
    private int visibleExportStatus;
}
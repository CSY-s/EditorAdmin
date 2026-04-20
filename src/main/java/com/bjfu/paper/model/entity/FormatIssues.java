// src/main/java/com/bjfu/paper/model/entity/FormatIssues.java
package com.bjfu.paper.model.entity;

import com.baomidou.mybatisplus.annotation.*;

@TableName("format_issues")
public class FormatIssues {
    
    @TableId(type = IdType.AUTO)
    private Long issueId;
    
    @TableField("ms_id")
    private String msId;
    
    @TableField("issue_type")
    private String issueType;
    
    @TableField("issue_description")
    private String issueDescription;
    
    private String severity;
    
    @TableField("check_id")
    private Long checkId;
    
    private String status;
    
    // 构造方法
    public FormatIssues() {
    }
    
    // Getter和Setter方法
    public Long getIssueId() {
        return issueId;
    }
    
    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }
    
    public String getMsId() {
        return msId;
    }
    
    public void setMsId(String msId) {
        this.msId = msId;
    }
    
    public String getIssueType() {
        return issueType;
    }
    
    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }
    
    public String getIssueDescription() {
        return issueDescription;
    }
    
    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public Long getCheckId() {
        return checkId;
    }
    
    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
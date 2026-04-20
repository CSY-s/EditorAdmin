// src/main/java/com/bjfu/paper/model/entity/TechnicalCheck.java
package com.bjfu.paper.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("technical_check")
public class TechnicalCheck {
    
    @TableId(type = IdType.AUTO)
    private Long checkId;
    
    @TableField("ms_id")
    private String msId;
    
    @TableField("checker_id")
    private Long checkerId;
    
    @TableField(value = "check_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    
    @TableField("word_count")
    private Integer wordCount;
    
    @TableField("word_count_status")
    private String wordCountStatus;
    
    @TableField("format_status")
    private String formatStatus;
    
    @TableField("plagiarism_rate")
    private Double plagiarismRate;
    
    @TableField("plagiarism_status")
    private String plagiarismStatus;
    
    @TableField("overall_result")
    private String overallResult;
    
    private String comments;
    
    @TableField("check_data")
    private String checkData; // JSON格式
    
    // 构造方法
    public TechnicalCheck() {
    }
    
    // Getter和Setter方法
    public Long getCheckId() {
        return checkId;
    }
    
    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }
    
    public String getMsId() {
        return msId;
    }
    
    public void setMsId(String msId) {
        this.msId = msId;
    }
    
    public Long getCheckerId() {
        return checkerId;
    }
    
    public void setCheckerId(Long checkerId) {
        this.checkerId = checkerId;
    }
    
    public Date getCheckTime() {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    
    public Integer getWordCount() {
        return wordCount;
    }
    
    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
    
    public String getWordCountStatus() {
        return wordCountStatus;
    }
    
    public void setWordCountStatus(String wordCountStatus) {
        this.wordCountStatus = wordCountStatus;
    }
    
    public String getFormatStatus() {
        return formatStatus;
    }
    
    public void setFormatStatus(String formatStatus) {
        this.formatStatus = formatStatus;
    }
    
    public Double getPlagiarismRate() {
        return plagiarismRate;
    }
    
    public void setPlagiarismRate(Double plagiarismRate) {
        this.plagiarismRate = plagiarismRate;
    }
    
    public String getPlagiarismStatus() {
        return plagiarismStatus;
    }
    
    public void setPlagiarismStatus(String plagiarismStatus) {
        this.plagiarismStatus = plagiarismStatus;
    }
    
    public String getOverallResult() {
        return overallResult;
    }
    
    public void setOverallResult(String overallResult) {
        this.overallResult = overallResult;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public String getCheckData() {
        return checkData;
    }
    
    public void setCheckData(String checkData) {
        this.checkData = checkData;
    }
}
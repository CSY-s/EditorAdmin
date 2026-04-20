// src/main/java/com/bjfu/paper/model/entity/Manuscript.java
package com.bjfu.paper.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

@TableName("manuscript")
public class Manuscript {
    
    @TableId(value = "ms_id", type = IdType.INPUT)
    private String msId;
    
    @TableField("submitter_id")
    private Long submitterId;
    
    @TableField("current_editor_id")
    private Long currentEditorId;
    
    @TableField("journal_id")
    private Long journalId;
    
    private String title;
    
    private String abstractText;
    
    private String keywords;
    
    @TableField("author_list")
    private String authorList;
    
    @TableField("research_topic")
    private String researchTopic;
    
    @TableField("funding_info")
    private String fundingInfo;
    
    private String status;
    
    @TableField("submit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    
    private String decision;
    
    @TableField("decision_reason")
    private String decisionReason;
    
    @TableField("form_review_result")
    private String formReviewResult;
    
    @TableField("form_review_feedback")
    private String formReviewFeedback;
    
    @TableField("current_version")
    private Integer currentVersion;
    
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    // 关联的作者信息（查询时使用）
    @TableField(exist = false)
    private String authorName;
    
    @TableField(exist = false)
    private String authorUnit;
    
    @TableField(exist = false)
    private List<File> files;

    @TableField(exist = false)
    private File latestManuscriptFile;
    
    
    
    @TableField("citation_count")
    private Integer citationCount = 0;  // 被引次数

    @TableField("download_count")
    private Integer downloadCount = 0;  // 下载次数

    @TableField("view_count")
    private Integer viewCount = 0;      // 查看次数

    @TableField("likes_count")
    private Integer likesCount = 0;     // 点赞次数

    @TableField("publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;           // 论文正式出版日期

    @TableField("is_published")
    private Integer isPublished = 0;    // 是否已发表：0-否 1-是
    
    // 构造方法
    public Manuscript() {
    }
    
    public Manuscript(String msId, String title, String status) {
        this.msId = msId;
        this.title = title;
        this.status = status;
    }
    
    // Getter和Setter方法
    public String getMsId() {
        return msId;
    }
    
    public void setMsId(String msId) {
        this.msId = msId;
    }
    
    public Long getSubmitterId() {
        return submitterId;
    }
    
    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }
    
    public Long getCurrentEditorId() {
        return currentEditorId;
    }
    
    public void setCurrentEditorId(Long currentEditorId) {
        this.currentEditorId = currentEditorId;
    }
    
    public Long getJournalId() {
        return journalId;
    }
    
    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAbstractText() {
        return abstractText;
    }
    
    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    
    public String getAuthorList() {
        return authorList;
    }
    
    public void setAuthorList(String authorList) {
        this.authorList = authorList;
    }
    
    public String getResearchTopic() {
        return researchTopic;
    }
    
    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic;
    }
    
    public String getFundingInfo() {
        return fundingInfo;
    }
    
    public void setFundingInfo(String fundingInfo) {
        this.fundingInfo = fundingInfo;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getSubmitTime() {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    
    public String getDecision() {
        return decision;
    }
    
    public void setDecision(String decision) {
        this.decision = decision;
    }
    
    public String getDecisionReason() {
        return decisionReason;
    }
    
    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }
    
    public String getFormReviewResult() {
        return formReviewResult;
    }
    
    public void setFormReviewResult(String formReviewResult) {
        this.formReviewResult = formReviewResult;
    }
    
    public String getFormReviewFeedback() {
        return formReviewFeedback;
    }
    
    public void setFormReviewFeedback(String formReviewFeedback) {
        this.formReviewFeedback = formReviewFeedback;
    }
    
    public Integer getCurrentVersion() {
        return currentVersion;
    }
    
    public void setCurrentVersion(Integer currentVersion) {
        this.currentVersion = currentVersion;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getAuthorUnit() {
        return authorUnit;
    }
    
    public void setAuthorUnit(String authorUnit) {
        this.authorUnit = authorUnit;
    }
    
    @Override
    public String toString() {
        return "Manuscript{" +
                "msId='" + msId + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", submitTime=" + submitTime +
                ", authorName='" + authorName + '\'' +
                '}';
    }
    
    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public File getLatestManuscriptFile() {
        return latestManuscriptFile;
    }

    public void setLatestManuscriptFile(File latestManuscriptFile) {
        this.latestManuscriptFile = latestManuscriptFile;
    }
    
    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(Integer citationCount) {
        this.citationCount = citationCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Integer isPublished) {
        this.isPublished = isPublished;
    }
}
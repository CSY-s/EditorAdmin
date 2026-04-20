// src/main/java/com/bjfu/paper/model/entity/JournalAnnouncement.java
package com.bjfu.paper.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("journal_announcement")
public class JournalAnnouncement {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String content;
    
    @TableField("announcement_type")
    private String announcementType;
    
    @TableField("effective_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;
    
    @TableField("expire_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;
    
    private Integer priority;
    
    @TableField("publish_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    
    @TableField("publisher_id")
    private Long publisherId;
    
    @TableField("publish_status")
    private String publishStatus;
    
    @TableField("schedule_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date scheduleTime;
    
    @TableField("is_top")
    private Integer isTop;
    
    @TableField("view_count")
    private Integer viewCount;
    
    @TableField("attachment_path")
    private String attachmentPath;
    
    @TableField("related_journal_id")
    private Long relatedJournalId;
    
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
    // 关联字段（非数据库字段）
    @TableField(exist = false)
    private String publisherName;
    
    @TableField(exist = false)
    private String journalName;
    
    // 构造方法
    public JournalAnnouncement() {
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getAnnouncementType() {
        return announcementType;
    }
    
    public void setAnnouncementType(String announcementType) {
        this.announcementType = announcementType;
    }
    
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public Date getExpireDate() {
        return expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public Date getPublishDate() {
        return publishDate;
    }
    
    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
    
    public Long getPublisherId() {
        return publisherId;
    }
    
    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }
    
    public String getPublishStatus() {
        return publishStatus;
    }
    
    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }
    
    public Date getScheduleTime() {
        return scheduleTime;
    }
    
    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
    
    public Integer getIsTop() {
        return isTop;
    }
    
    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }
    
    public Integer getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    
    public String getAttachmentPath() {
        return attachmentPath;
    }
    
    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
    
    public Long getRelatedJournalId() {
        return relatedJournalId;
    }
    
    public void setRelatedJournalId(Long relatedJournalId) {
        this.relatedJournalId = relatedJournalId;
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
    
    public String getPublisherName() {
        return publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public String getJournalName() {
        return journalName;
    }
    
    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }
    
    @Override
    public String toString() {
        return "JournalAnnouncement{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", announcementType='" + announcementType + '\'' +
                ", publishStatus='" + publishStatus + '\'' +
                ", isTop=" + isTop +
                ", priority=" + priority +
                '}';
    }
}
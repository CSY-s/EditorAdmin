// News.java - 修复后的版本
package com.bjfu.paper.model.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

@TableName("news")  // 指定表名
public class News {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    private String content;
    
    @TableField("publish_date")
    private Date publishDate;
    
    @TableField("publisher_id")
    private Long publisherId;
    
    @TableField("is_notice")
    private Integer isNotice;
    
    @TableField("attachment_path")
    private String attachmentPath;
    
    @TableField("publish_status")
    private String publishStatus;
    
    @TableField("schedule_time")
    private Date scheduleTime;
    
    @TableField("news_type")
    private String newsType;

 // 无参构造方法
    public News() {
    }

    // Getter 和 Setter 方法
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

    public Integer getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(Integer isNotice) {
        this.isNotice = isNotice;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
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

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", publishStatus='" + publishStatus + '\'' +
                ", newsType='" + newsType + '\'' +
                '}';
    }
}
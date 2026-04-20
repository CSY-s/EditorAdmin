// src/main/java/com/bjfu/paper/model/entity/FileEntity.java
package com.bjfu.paper.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@TableName("file")
public class File {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField("file_name")
    private String fileName;
    
    @TableField("file_path")
    private String filePath;
    
    @TableField("file_type")
    private String fileType;
    
    @TableField("file_size")
    private Long fileSize;
    
    @TableField("ms_id")
    private String msId;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("file_role")
    private String fileRole;
    
    @TableField(value = "upload_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;
    
    // 新增：获取完整的文件路径
    public String getFullPath() {
        return System.getProperty("user.dir") + "/upload/" + filePath;
    }
    
    // 构造方法
    public File() {
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFileType() {
        return fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public Long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    public String getMsId() {
        return msId;
    }
    
    public void setMsId(String msId) {
        this.msId = msId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getFileRole() {
        return fileRole;
    }
    
    public void setFileRole(String fileRole) {
        this.fileRole = fileRole;
    }
    
    public Date getUploadTime() {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
    
    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", msId='" + msId + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
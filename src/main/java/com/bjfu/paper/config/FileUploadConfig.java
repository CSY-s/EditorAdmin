// src/main/java/com/bjfu/paper/config/FileUploadConfig.java
package com.bjfu.paper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    
    @Value("${file.upload.base-path:./upload/}")
    private String uploadBasePath;
    
    @Value("${file.upload.versions-path:versions/original/}")
    private String versionsPath;
    
    @PostConstruct
    public void init() {
        // 创建必要的目录
        createDirectories();
    }
    
    private void createDirectories() {
        // 创建上传根目录
        File uploadDir = new File(uploadBasePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
            System.out.println("创建上传目录: " + uploadDir.getAbsolutePath());
        }
        
        // 创建版本目录
        File versionsDir = new File(uploadBasePath + versionsPath);
        if (!versionsDir.exists()) {
            versionsDir.mkdirs();
            System.out.println("创建版本目录: " + versionsDir.getAbsolutePath());
        }
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源访问
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadBasePath);
        
        // 确保上传目录存在
        File uploadDir = new File(uploadBasePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }
}
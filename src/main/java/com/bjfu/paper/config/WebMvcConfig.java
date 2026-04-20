package com.bjfu.paper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Value("${file.upload.base-path:./upload/}")
    private String uploadBasePath;
    
    /**
     * 初始化时确保上传目录存在
     */
    @PostConstruct
    public void init() {
        try {
            // 规范化路径
            String normalizedPath = Paths.get(uploadBasePath).normalize().toAbsolutePath().toString();
            
            // 确保路径以斜杠结尾
            if (!normalizedPath.endsWith(File.separator)) {
                normalizedPath += File.separator;
            }
            
            File uploadDir = new File(normalizedPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                System.out.println("创建上传目录: " + normalizedPath + " - " + (created ? "成功" : "失败"));
            }
            
            System.out.println("上传目录配置:");
            System.out.println("  - 原始配置: " + uploadBasePath);
            System.out.println("  - 实际路径: " + normalizedPath);
            System.out.println("  - 目录存在: " + uploadDir.exists());
            System.out.println("  - 可读: " + uploadDir.canRead());
            System.out.println("  - 可写: " + uploadDir.canWrite());
            
            // 创建子目录
            createSubDirectories(normalizedPath);
            
        } catch (Exception e) {
            System.err.println("初始化上传目录失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建必要的子目录
     */
    private void createSubDirectories(String basePath) {
        String[] subDirs = {
            "news/images/",
            "news/attachments/",
            "announcements/images/",
            "announcements/attachments/"
        };
        
        for (String subDir : subDirs) {
            File dir = new File(basePath + subDir);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("创建子目录: " + dir.getAbsolutePath());
            }
        }
    }
    
    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取规范化后的路径
        String normalizedPath;
        try {
            normalizedPath = Paths.get(uploadBasePath).normalize().toAbsolutePath().toString();
            if (!normalizedPath.endsWith(File.separator)) {
                normalizedPath += File.separator;
            }
        } catch (Exception e) {
            normalizedPath = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
        }
        
        System.out.println("静态资源映射配置:");
        System.out.println("  - 上传文件访问路径: /upload/**");
        System.out.println("  - 映射到物理路径: file:" + normalizedPath);
        
        // 映射静态资源（HTML/CSS/JS/图片等）
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600);
        
        // 映射上传文件 - 关键修正
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + normalizedPath)
                .setCachePeriod(0); // 上传文件不缓存
        
        // 映射webjars（Bootstrap/jQuery等）
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(3600);
        
        // 映射back目录下的HTML文件
        registry.addResourceHandler("/back/**")
                .addResourceLocations("classpath:/static/back/")
                .setCachePeriod(3600);
        
        // 添加字体文件支持
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/", "classpath:/fonts/")
                .setCachePeriod(3600);
        
        System.out.println("静态资源映射完成");
    }
    
    /**
     * 配置JSP视图解析器
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 配置 JSP 视图解析器
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }
    
    /**
     * 获取上传目录的绝对路径（用于调试）
     */
    public String getUploadAbsolutePath() {
        try {
            return Paths.get(uploadBasePath).normalize().toAbsolutePath().toString();
        } catch (Exception e) {
            return uploadBasePath;
        }
    }
    
    /**
     * 获取上传目录的URL相对路径
     */
    public String getUploadRelativePath() {
        return "/upload/";
    }
}
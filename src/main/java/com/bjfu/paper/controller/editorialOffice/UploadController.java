// src/main/java/com/bjfu/paper/controller/editorialOffice/UploadController.java
package com.bjfu.paper.controller.editorialOffice;

import com.bjfu.paper.model.dto.Result;
import com.bjfu.paper.util.ResultUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/editorial-office/upload")
public class UploadController {
    
    @Value("${file.upload.base-path:./upload/}")
    private String uploadBasePath;
    
    @Value("${file.upload.news-image-path:news/images/}")
    private String newsImagePath;
    
    @Value("${file.upload.announcement-image-path:announcements/images/}")
    private String announcementImagePath;
    
    @Value("${file.upload.news-attachment-path:news/attachments/}")
    private String newsAttachmentPath;
    
    @Value("${file.upload.announcement-attachment-path:announcements/attachments/}")
    private String announcementAttachmentPath;
    
    @Value("${file.upload.document-allowed-types:pdf,doc,docx,xls,xlsx,ppt,pptx,txt,rtf}")
    private String documentAllowedTypes;
    
    @Value("${file.upload.video-allowed-types:mp4,avi,mov,wmv,flv,webm,mkv,mpg,mpeg,3gp}") // 添加视频类型配置
    private String videoAllowedTypes;
    
    @Value("${file.upload.allowed-types:jpg,jpeg,png,gif,webp,bmp,pdf,doc,docx,xls,xlsx,ppt,pptx,txt,rtf,zip,rar,mp4,avi,mov,wmv,flv,webm,mkv,mpg,mpeg,3gp}") // 添加所有允许类型
    private String allAllowedTypes;
    
    @Value("${server.port:2008}")
    private String serverPort;
    
    /**
     * 上传新闻图片
     */
    @PostMapping("/news-image")
    public Result uploadNewsImage(@RequestParam("file") MultipartFile file) {
        return uploadImage(file, newsImagePath, "image");
    }
    
    /**
     * 上传公告图片
     */
    @PostMapping("/announcement-image")
    public Result uploadAnnouncementImage(@RequestParam("file") MultipartFile file) {
        return uploadImage(file, announcementImagePath, "image");
    }
    
    /**
     * 上传新闻附件（PDF、Word等）
     */
    @PostMapping("/news-attachment")
    public Result uploadNewsAttachment(@RequestParam("file") MultipartFile file) {
        return uploadAttachment(file, newsAttachmentPath, "document");
    }
    
    /**
     * 上传公告附件（PDF、Word等）
     */
    @PostMapping("/announcement-attachment")
    public Result uploadAnnouncementAttachment(@RequestParam("file") MultipartFile file) {
        return uploadAttachment(file, announcementAttachmentPath, "document");
    }
    
    /**
     * 通用图片上传方法
     */
    private Result uploadImage(MultipartFile file, String subPath, String fileType) {
        try {
            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResultUtil.error("只允许上传图片文件");
            }
            
            // 验证文件大小（限制为5MB）
            long maxSize = 5 * 1024 * 1024; // 5MB
            if (file.getSize() > maxSize) {
                return ResultUtil.error("图片大小不能超过5MB");
            }
            
            return uploadFile(file, subPath, fileType);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 通用附件上传方法（支持文档、图片、压缩文件和视频）
     */
    private Result uploadAttachment(MultipartFile file, String subPath, String fileType) {
        try {
            // 验证文件类型
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            
            System.out.println("上传附件调试信息:");
            System.out.println("  - 文件名: " + originalFilename);
            System.out.println("  - 扩展名: " + fileExtension);
            System.out.println("  - 内容类型: " + contentType);
            
            // 允许的文件类型：文档 + 视频 + 图片 + 压缩文件
            String[] allowedExtensions = allAllowedTypes.split(",");
            boolean isAllowed = false;
            for (String ext : allowedExtensions) {
                if (ext.trim().equalsIgnoreCase(fileExtension)) {
                    isAllowed = true;
                    break;
                }
            }
            
            if (!isAllowed) {
                return ResultUtil.error("不支持的文件类型: " + fileExtension + "，只允许上传: " + allAllowedTypes);
            }
            
            // 根据文件类型设置不同的文件大小限制
            long maxSize;
            if (isVideoFile(fileExtension)) {
                maxSize = 100 * 1024 * 1024; // 视频100MB
                System.out.println("  - 识别为视频文件，限制大小: 100MB");
            } else if (isImageFile(fileExtension)) {
                maxSize = 5 * 1024 * 1024; // 图片5MB
            } else if (isZipFile(fileExtension)) {
                maxSize = 50 * 1024 * 1024; // 压缩文件50MB
            } else {
                maxSize = 10 * 1024 * 1024; // 文档10MB
            }
            
            if (file.getSize() > maxSize) {
                return ResultUtil.error("文件大小不能超过" + (maxSize / (1024 * 1024)) + "MB");
            }
            
            // 上传文件
            Result result = uploadFile(file, subPath, fileType);
            
            // 如果是视频文件，添加额外的调试信息
            if (isVideoFile(fileExtension)) {
                System.out.println("视频文件上传完成:");
                Map<String, Object> data = (Map<String, Object>) result.getData();
                if (data != null) {
                    System.out.println("  - URL: " + data.get("url"));
                    System.out.println("  - Full URL: " + data.get("fullUrl"));
                    System.out.println("  - MIME Type: " + data.get("type"));
                }
            }
            
            return result;
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 通用文件上传方法
     */
    private Result uploadFile(MultipartFile file, String subPath, String fileType) throws IOException {
        // 添加调试信息
        System.out.println("开始上传文件:");
        System.out.println("  - 原始文件名: " + file.getOriginalFilename());
        System.out.println("  - 文件大小: " + file.getSize() + " bytes");
        System.out.println("  - 内容类型: " + file.getContentType());
        System.out.println("  - 上传路径: " + subPath);
        System.out.println("  - 文件类型: " + fileType);
        
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
        
        // 按日期创建目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = sdf.format(new Date());
        String uploadPath = uploadBasePath + subPath + datePath;
        
        // 确保uploadBasePath不以斜杠结尾
        if (uploadBasePath.endsWith("/") && subPath.startsWith("/")) {
            uploadPath = uploadBasePath.substring(0, uploadBasePath.length() - 1) + subPath + datePath;
        }
        
        System.out.println("  - 完整上传路径: " + uploadPath);
        
        // 创建目录
        File directory = new File(uploadPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("  - 创建目录: " + created);
        }
        
        System.out.println("  - 目录权限: 可读=" + directory.canRead() + ", 可写=" + directory.canWrite());
        
        // 保存文件
        String fullPath = uploadPath + "/" + fileName;
        Path path = Paths.get(fullPath);
        
        System.out.println("  - 文件保存路径: " + fullPath);
        
        try {
            Files.write(path, file.getBytes());
            System.out.println("  - 文件保存成功");
        } catch (IOException e) {
            System.err.println("  - 文件保存失败: " + e.getMessage());
            throw e;
        }
        
        String url = "/upload/" + subPath + datePath + "/" + fileName;
        
        // 清理URL中的问题
        url = url.replace("//", "/");
        url = url.replace("//", "/"); // 多次替换确保没有双斜杠
        url = url.replace("upload/upload", "upload"); // 处理可能的重复
        
        System.out.println("  - 处理后URL: " + url);
        
        String fullUrl;
        String environment = System.getenv("SPRING_PROFILES_ACTIVE");
        if ("production".equals(environment)) {
            fullUrl = "https://your-domain.com" + url;
        } else {
            // 开发环境 - 使用绝对路径
            String serverUrl = "http://localhost:" + serverPort; // 需要获取服务器端口
            fullUrl = serverUrl + url;
        }
        
        System.out.println("  - 完整URL: " + fullUrl);
        
        // 获取正确的MIME类型
        String mimeType = getMimeType(originalFilename);
        System.out.println("  - MIME类型: " + mimeType);
        
        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("fullUrl", fullUrl);
        result.put("relativeUrl", url);
        result.put("name", originalFilename);
        result.put("size", file.getSize());
        result.put("type", mimeType);
        result.put("fileType", fileType);
        result.put("extension", fileExtension);
        
        // 添加视频文件的特殊信息
        if (isVideoFile(fileExtension)) {
            result.put("isVideo", true);
            result.put("videoType", getVideoType(fileExtension));
            result.put("compatibility", getVideoCompatibility(fileExtension));
        }
        
        return ResultUtil.success("上传成功", result);
    }
    
    /**
     * 获取视频格式兼容性信息
     */
    private Map<String, Object> getVideoCompatibility(String extension) {
        Map<String, Object> compatibility = new HashMap<>();
        String ext = extension.toLowerCase();
        
        switch (ext) {
            case "mp4":
                compatibility.put("level", "high");
                compatibility.put("message", "MP4格式在所有现代浏览器中都得到良好支持");
                compatibility.put("recommended", true);
                compatibility.put("browsers", "Chrome, Firefox, Safari, Edge, Opera");
                break;
            case "webm":
                compatibility.put("level", "medium");
                compatibility.put("message", "WebM格式在大多数现代浏览器中支持");
                compatibility.put("recommended", true);
                compatibility.put("browsers", "Chrome, Firefox, Edge, Opera");
                break;
            case "ogg":
                compatibility.put("level", "medium");
                compatibility.put("message", "Ogg格式在Firefox和Chrome中支持");
                compatibility.put("recommended", true);
                compatibility.put("browsers", "Firefox, Chrome, Opera");
                break;
            default:
                compatibility.put("level", "low");
                compatibility.put("message", "该格式可能需要额外的解码器或插件");
                compatibility.put("recommended", false);
                compatibility.put("browsers", "视具体格式而定");
        }
        
        return compatibility;
    }
    
    /**
     * 获取视频类型信息
     */
    private String getVideoType(String extension) {
        switch (extension.toLowerCase()) {
            case "mp4":
                return "MPEG-4";
            case "avi":
                return "Audio Video Interleave";
            case "mov":
                return "QuickTime";
            case "webm":
                return "WebM";
            case "mkv":
                return "Matroska";
            case "flv":
                return "Flash Video";
            default:
                return extension.toUpperCase();
        }
    }
    
    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
    
    /**
     * 删除上传的文件
     */
    @DeleteMapping("/delete-file")
    public Result deleteFile(@RequestParam String url) {
        try {
            // 从URL中提取文件路径
            if (url.startsWith("/upload/")) {
                String relativePath = url.substring(7); // 去掉"/upload/"
                String filePath = uploadBasePath + relativePath;
                
                File file = new File(filePath);
                if (file.exists() && file.isFile()) {
                    if (file.delete()) {
                        return ResultUtil.success("删除成功");
                    } else {
                        return ResultUtil.error("删除失败");
                    }
                } else {
                    return ResultUtil.error("文件不存在");
                }
            } else {
                return ResultUtil.error("无效的URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("删除失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传视频
     */
    @PostMapping("/video")
    public Result uploadVideo(@RequestParam("file") MultipartFile file) {
        return uploadVideoFile(file, "video");
    }
    
    /**
     * 判断是否为视频文件
     */
    private boolean isVideoFile(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }
        String[] videoExtensions = videoAllowedTypes.split(",");
        for (String ext : videoExtensions) {
            if (ext.trim().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否为图片文件
     */
    private boolean isImageFile(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "webp", "bmp"};
        for (String ext : imageExtensions) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断是否为文档文件
     */
    private boolean isDocumentFile(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }
        String[] documentExtensions = documentAllowedTypes.split(",");
        for (String ext : documentExtensions) {
            if (ext.trim().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为压缩文件
     */
    private boolean isZipFile(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }
        String[] zipExtensions = {"zip", "rar", "7z", "tar", "gz"};
        for (String ext : zipExtensions) {
            if (ext.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 根据文件扩展名获取MIME类型
     */
    private String getMimeType(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        
        Map<String, String> mimeTypes = new HashMap<>();
        // 图片类型
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("webp", "image/webp");
        mimeTypes.put("bmp", "image/bmp");
        
        // 文档类型
        mimeTypes.put("pdf", "application/pdf");
        mimeTypes.put("doc", "application/msword");
        mimeTypes.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeTypes.put("xls", "application/vnd.ms-excel");
        mimeTypes.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mimeTypes.put("ppt", "application/vnd.ms-powerpoint");
        mimeTypes.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mimeTypes.put("txt", "text/plain");
        mimeTypes.put("rtf", "application/rtf");
        
        // 压缩文件
        mimeTypes.put("zip", "application/zip");
        mimeTypes.put("rar", "application/x-rar-compressed");
        
        // 视频类型 - 添加详细的MIME类型
        mimeTypes.put("mp4", "video/mp4");
        mimeTypes.put("avi", "video/x-msvideo");
        mimeTypes.put("mov", "video/quicktime");
        mimeTypes.put("wmv", "video/x-ms-wmv");
        mimeTypes.put("flv", "video/x-flv");
        mimeTypes.put("webm", "video/webm");
        mimeTypes.put("mkv", "video/x-matroska");
        mimeTypes.put("mpg", "video/mpeg");
        mimeTypes.put("mpeg", "video/mpeg");
        mimeTypes.put("3gp", "video/3gpp");
        
        return mimeTypes.getOrDefault(extension, "application/octet-stream");
    }


    /**
     * 通用视频上传方法
     */
    private Result uploadVideoFile(MultipartFile file, String fileType) {
        try {
            // 验证文件类型
            String contentType = file.getContentType();
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            
            // 允许的视频类型
            String[] allowedExtensions = {"mp4", "avi", "mov", "wmv", "flv", "webm"};
            boolean isAllowed = false;
            for (String ext : allowedExtensions) {
                if (ext.trim().equalsIgnoreCase(fileExtension)) {
                    isAllowed = true;
                    break;
                }
            }
            
            if (!isAllowed) {
                return ResultUtil.error("不支持的视频格式，只允许上传: " + String.join(", ", allowedExtensions));
            }
            
            // 验证文件大小（限制为50MB）
            long maxSize = 50 * 1024 * 1024; // 50MB
            if (file.getSize() > maxSize) {
                return ResultUtil.error("视频大小不能超过50MB");
            }
            
            // 上传到附件目录
            return uploadFile(file, "attachments/", fileType);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("上传失败: " + e.getMessage());
        }
    }
}
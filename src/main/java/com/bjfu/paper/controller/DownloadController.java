package com.bjfu.paper.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloadController {

    @Value("${file.upload.base-path:./upload/}")
    private String uploadBasePath;

    @GetMapping("/download")
    public ResponseEntity<Resource> download(
            @RequestParam("path") String path,
            @RequestParam(value = "filename", required = false) String filename) throws Exception {

        Path uploadRoot = Paths.get(uploadBasePath).normalize().toAbsolutePath();
        String normalizedRequestPath = path.replace("\\", "/");
        if (normalizedRequestPath.startsWith("/upload/")) {
            normalizedRequestPath = normalizedRequestPath.substring("/upload/".length());
        } else if (normalizedRequestPath.startsWith("upload/")) {
            normalizedRequestPath = normalizedRequestPath.substring("upload/".length());
        } else if (normalizedRequestPath.startsWith("/")) {
            normalizedRequestPath = normalizedRequestPath.substring(1);
        }

        Path targetFile = uploadRoot.resolve(normalizedRequestPath).normalize();
        if (!targetFile.startsWith(uploadRoot) || !targetFile.toFile().exists() || !targetFile.toFile().isFile()) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = toResource(targetFile);
        String downloadName = (filename == null || filename.trim().isEmpty())
                ? targetFile.getFileName().toString()
                : filename.trim();
        String safeDownloadName = new File(downloadName).getName();
        String encodedName = URLEncoder.encode(safeDownloadName, StandardCharsets.UTF_8.name()).replace("+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + safeDownloadName + "\"; filename*=UTF-8''" + encodedName)
                .body(resource);
    }

    private Resource toResource(Path targetFile) throws MalformedURLException {
        return new UrlResource(targetFile.toUri());
    }
}

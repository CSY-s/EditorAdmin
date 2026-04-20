// src/main/java/com/bjfu/paper/controller/api/PlagiarismApiController.java
package com.bjfu.paper.controller.api;

import com.bjfu.paper.model.dto.Result;
import com.bjfu.paper.model.dto.PlagiarismCheckResult;
import com.bjfu.paper.model.dto.PlagiarismServiceInfo;
import com.bjfu.paper.service.external.PlagiarismServiceManager;
import com.bjfu.paper.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plagiarism")
public class PlagiarismApiController {
    
    @Autowired
    private PlagiarismServiceManager plagiarismServiceManager;
    
    /**
     * 获取可用的查重服务列表
     */
    @GetMapping("/services")
    public Result getAvailableServices() {
        try {
            List<PlagiarismServiceInfo> services = plagiarismServiceManager.getAllAvailableServices();
            return ResultUtil.success(services);
        } catch (Exception e) {
            return ResultUtil.error("获取查重服务失败: " + e.getMessage());
        }
    }
    
    /**
     * 执行查重检查
     */
    @PostMapping("/check")
    public Result performPlagiarismCheck(@RequestBody Map<String, Object> request) {
        try {
            String msId = (String) request.get("msId");
            String serviceName = (String) request.get("service");
            
            // 这里需要获取稿件文件路径
            // 简化实现，实际需要从数据库获取
            String filePath = getManuscriptFilePath(msId);
            
            if (filePath == null) {
                return ResultUtil.error("找不到稿件文件");
            }
            
            // 设置指定的查重服务
            if (serviceName != null) {
                plagiarismServiceManager.setDefaultService(serviceName);
            }
            
            // 执行查重
            PlagiarismCheckResult result = plagiarismServiceManager.performPlagiarismCheck(
                msId, filePath, "pdf");
            
            return ResultUtil.success(result);
            
        } catch (Exception e) {
            return ResultUtil.error("查重检查失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取查重报告
     */
    @GetMapping("/report/{msId}")
    public Result getPlagiarismReport(@PathVariable String msId) {
        try {
            // 这里应该返回生成的查重报告
            // 可以是HTML、PDF或JSON格式
            return ResultUtil.success("查重报告获取成功（示例）");
        } catch (Exception e) {
            return ResultUtil.error("获取报告失败: " + e.getMessage());
        }
    }
    
    /**
     * 设置默认查重服务
     */
    @PostMapping("/service/default")
    public Result setDefaultService(@RequestBody Map<String, String> request) {
        try {
            String serviceName = request.get("serviceName");
            plagiarismServiceManager.setDefaultService(serviceName);
            return ResultUtil.success("默认查重服务已设置为: " + serviceName);
        } catch (Exception e) {
            return ResultUtil.error("设置失败: " + e.getMessage());
        }
    }
    
    private String getManuscriptFilePath(String msId) {
        // 从数据库获取稿件文件路径
        // 这里简化实现
        return "/upload/manuscripts/" + msId + ".pdf";
    }
}
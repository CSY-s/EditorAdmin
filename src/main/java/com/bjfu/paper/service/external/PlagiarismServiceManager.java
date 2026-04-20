package com.bjfu.paper.service.external;

import com.bjfu.paper.model.dto.PlagiarismCheckResult;
import com.bjfu.paper.model.dto.PlagiarismServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlagiarismServiceManager {
    
    @Autowired(required = false)
    private Map<String, PlagiarismCheckService> plagiarismServices = new HashMap<>();
    
    private String defaultService = "grammarlyService";
    
    /**
     * 执行查重检查
     */
    public PlagiarismCheckResult performPlagiarismCheck(String msId, String filePath, String fileType) {
        PlagiarismCheckService service = getActiveService();
        
        if (service == null) {
            throw new RuntimeException("无可用的查重服务");
        }
        
        try {
            return service.checkByFile(msId, filePath, msId + "_manuscript." + fileType.toLowerCase());
        } catch (Exception e) {
            return tryFallbackServices(msId, filePath, fileType, e);
        }
    }
    
    /**
     * 获取活动的查重服务
     */
    private PlagiarismCheckService getActiveService() {
        // 1. 检查默认服务
        PlagiarismCheckService defaultService = plagiarismServices.get(this.defaultService);
        if (defaultService != null && isServiceAvailable(defaultService)) {
            return defaultService;
        }
        
        // 2. 查找第一个可用的服务
        for (PlagiarismCheckService service : plagiarismServices.values()) {
            if (isServiceAvailable(service)) {
                return service;
            }
        }
        
        return null;
    }
    
    /**
     * 检查服务是否可用
     */
    private boolean isServiceAvailable(PlagiarismCheckService service) {
        try {
            List<PlagiarismServiceInfo> services = service.getAvailableServices();
            return services.stream()
                .anyMatch(PlagiarismServiceInfo::isEnabled);  // 修复这里
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 尝试备用服务
     */
    private PlagiarismCheckResult tryFallbackServices(String msId, String filePath, String fileType, Exception originalError) {
        for (Map.Entry<String, PlagiarismCheckService> entry : plagiarismServices.entrySet()) {
            if (entry.getKey().equals(defaultService)) {
                continue;
            }
            
            try {
                return entry.getValue().checkByFile(msId, filePath, msId + "_manuscript." + fileType.toLowerCase());
            } catch (Exception e) {
                System.err.println("查重服务 " + entry.getKey() + " 失败: " + e.getMessage());
            }
        }
        
        throw new RuntimeException("所有查重服务都失败，原始错误: " + originalError.getMessage(), originalError);
    }
    
    /**
     * 获取所有可用的查重服务信息
     */
    public List<PlagiarismServiceInfo> getAllAvailableServices() {
        return plagiarismServices.values().stream()
            .flatMap(service -> service.getAvailableServices().stream())
            .filter(PlagiarismServiceInfo::isEnabled)
            .collect(Collectors.toList());
    }
    
    /**
     * 设置默认服务
     */
    public void setDefaultService(String serviceName) {
        this.defaultService = serviceName;
    }
}
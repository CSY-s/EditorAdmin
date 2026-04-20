package com.bjfu.paper.service.external;

import com.bjfu.paper.model.dto.PlagiarismCheckResult;
import com.bjfu.paper.model.dto.PlagiarismServiceInfo;

import java.util.List;

public interface PlagiarismCheckService {
    
    /**
     * 上传文件并获取查重报告
     */
    PlagiarismCheckResult checkByFile(String msId, String filePath, String fileName);
    
    /**
     * 通过文本内容查重
     */
    PlagiarismCheckResult checkByText(String msId, String text);
    
    /**
     * 获取支持的查重服务列表
     */
    List<PlagiarismServiceInfo> getAvailableServices();
    
    /**
     * 设置默认的查重服务
     */
    void setDefaultService(String serviceName);
}
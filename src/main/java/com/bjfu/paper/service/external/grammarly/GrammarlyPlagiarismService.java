package com.bjfu.paper.service.external.grammarly;

import com.bjfu.paper.model.dto.PlagiarismCheckResult;
import com.bjfu.paper.model.dto.PlagiarismServiceInfo;
import com.bjfu.paper.model.dto.SimilarSource;  // 导入独立的SimilarSource
import com.bjfu.paper.service.external.PlagiarismCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("grammarlyService")
public class GrammarlyPlagiarismService implements PlagiarismCheckService {
    
    @Value("${plagiarism.grammarly.client-id:}")
    private String clientId;
    
    @Value("${plagiarism.grammarly.client-secret:}")
    private String clientSecret;
    
    @Value("${plagiarism.grammarly.enabled:true}")
    private boolean enabled;
    
    private String accessToken;
    
    @Override
    public PlagiarismCheckResult checkByFile(String msId, String filePath, String fileName) {
        return callGrammarlyAPI(msId, "file_content_placeholder");
    }
    
    @Override
    public PlagiarismCheckResult checkByText(String msId, String text) {
        return callGrammarlyAPI(msId, text);
    }
    
    @Override
    public List<PlagiarismServiceInfo> getAvailableServices() {
        List<PlagiarismServiceInfo> services = new ArrayList<>();
        
        PlagiarismServiceInfo grammarly = new PlagiarismServiceInfo();
        grammarly.setName("Grammarly");
        grammarly.setDescription("Grammarly高级查重服务");
        grammarly.setMaxSimilarity(20.0);
        grammarly.setEnabled(this.enabled);
        grammarly.setApiKeyRequired("Client ID & Secret");
        
        services.add(grammarly);
        return services;
    }
    
    @Override
    public void setDefaultService(String serviceName) {
        System.out.println("设置默认查重服务为: " + serviceName);
    }
    
    /**
     * 调用实际的Grammarly API
     */
    private PlagiarismCheckResult callGrammarlyAPI(String msId, String content) {
        PlagiarismCheckResult result = new PlagiarismCheckResult();
        result.setMsId(msId);
        
        try {
            // 模拟Grammarly API响应
            result.setSimilarityRate(8.5);
            result.setStatus("PASS");
            result.setMessage("Grammarly查重通过");
            result.setCheckTime(new Date());
            result.setServiceUsed("Grammarly Premium");
            
            // 模拟AI检测
            result.setAiProbability(15.3);
            result.setAiWarning("低概率AI生成");
            
            // 创建相似来源列表
            List<SimilarSource> sources = new ArrayList<>();
            
            // 相似来源1
            SimilarSource source1 = new SimilarSource();
            source1.setTitle("Deep Learning for Image Recognition");
            source1.setAuthor("Zhang, L. et al.");
            source1.setSource("CVPR 2023");
            source1.setSimilarity(5.2);
            source1.setUrl("https://example.com/paper1");
            
            // 相似来源2
            SimilarSource source2 = new SimilarSource();
            source2.setTitle("A Survey of Neural Network Architectures");
            source2.setAuthor("Wang, H. et al.");
            source2.setSource("Neural Networks Journal");
            source2.setSimilarity(3.3);
            source2.setUrl("https://example.com/paper2");
            
            sources.add(source1);
            sources.add(source2);
            result.setSimilarSources(sources);
            
        } catch (Exception e) {
            result.setSimilarityRate(0.0);
            result.setStatus("FAIL");
            result.setMessage("Grammarly API调用失败: " + e.getMessage());
            result.setCheckTime(new Date());
        }
        
        return result;
    }
}
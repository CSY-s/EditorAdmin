package com.bjfu.paper.model.dto;

import java.util.Date;
import java.util.List;

public class PlagiarismCheckResult {
    private String msId;
    private double similarityRate;
    private String reportUrl;
    private List<SimilarSource> similarSources;  // 直接使用SimilarSource
    private String status;
    private String message;
    private Date checkTime;
    private String serviceUsed;
    
    // AI检测相关字段
    private Double aiProbability;
    private String aiWarning;
    
    // getters and setters
    public String getMsId() { return msId; }
    public void setMsId(String msId) { this.msId = msId; }
    
    public double getSimilarityRate() { return similarityRate; }
    public void setSimilarityRate(double similarityRate) { this.similarityRate = similarityRate; }
    
    public String getReportUrl() { return reportUrl; }
    public void setReportUrl(String reportUrl) { this.reportUrl = reportUrl; }
    
    public List<SimilarSource> getSimilarSources() { return similarSources; }
    public void setSimilarSources(List<SimilarSource> similarSources) { this.similarSources = similarSources; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Date getCheckTime() { return checkTime; }
    public void setCheckTime(Date checkTime) { this.checkTime = checkTime; }
    
    public String getServiceUsed() { return serviceUsed; }
    public void setServiceUsed(String serviceUsed) { this.serviceUsed = serviceUsed; }
    
    public Double getAiProbability() { return aiProbability; }
    public void setAiProbability(Double aiProbability) { this.aiProbability = aiProbability; }
    
    public String getAiWarning() { return aiWarning; }
    public void setAiWarning(String aiWarning) { this.aiWarning = aiWarning; }
}
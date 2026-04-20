package com.bjfu.paper.model.dto;

public class PlagiarismServiceInfo {
    private String name;
    private String description;
    private double maxSimilarity;
    private boolean enabled;
    private String apiKeyRequired;
    
    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getMaxSimilarity() { return maxSimilarity; }
    public void setMaxSimilarity(double maxSimilarity) { this.maxSimilarity = maxSimilarity; }
    
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public String getApiKeyRequired() { return apiKeyRequired; }
    public void setApiKeyRequired(String apiKeyRequired) { this.apiKeyRequired = apiKeyRequired; }
}
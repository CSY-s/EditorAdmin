package com.bjfu.paper.model.dto;

public class SimilarSource {
    private String title;
    private String author;
    private String source;
    private double similarity;
    private String url;
    
    // 构造方法
    public SimilarSource() {}
    
    public SimilarSource(String title, String author, String source, double similarity, String url) {
        this.title = title;
        this.author = author;
        this.source = source;
        this.similarity = similarity;
        this.url = url;
    }
    
    // getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public double getSimilarity() { return similarity; }
    public void setSimilarity(double similarity) { this.similarity = similarity; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
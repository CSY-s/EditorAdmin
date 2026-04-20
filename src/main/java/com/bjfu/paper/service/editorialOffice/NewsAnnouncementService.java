// NewsAnnouncementService.java
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.model.entity.News;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface NewsAnnouncementService {
    
    // 新增新闻/公告
    boolean addNews(News news);
    
    // 更新新闻/公告
    boolean updateNews(News news);
    
    // 删除新闻/公告
    boolean deleteNews(Long id);
    
    // 获取新闻列表（分页）
    List<News> getNewsList(int page, int pageSize);
    
    // 获取新闻列表（带条件分页）
    List<News> getNewsWithPagination(int page, int pageSize, Map<String, Object> condition);
    
    // 获取公告列表
    List<News> getNoticeList();
    
    // 根据ID获取新闻详情
    News getNewsDetail(Long id);
    
    // 发布新闻（改变状态）
    boolean publishNews(Long id);
    
    // 定时发布新闻
    boolean scheduleNews(Long id, Date scheduleTime);
    
    // 获取统计数据
    Map<String, Object> getStatistics();
    
    // 获取所有新闻（不分页）
    List<News> getAllNews();
    
    // 搜索新闻
    List<News> searchNews(String keyword);
    
    // 筛选新闻
    List<News> filterNews(Map<String, Object> filters);
    
    // 批量删除
    boolean batchDeleteNews(List<Long> ids);
    
    // 添加：根据条件统计新闻数量
    int countNewsByCondition(Map<String, Object> condition);
}
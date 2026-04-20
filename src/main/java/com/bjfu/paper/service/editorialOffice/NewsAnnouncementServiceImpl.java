// NewsAnnouncementServiceImpl.java - 修改后的完整实现
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.mapper.NewsMapper;
import com.bjfu.paper.model.entity.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class NewsAnnouncementServiceImpl implements NewsAnnouncementService {

    private static final Logger logger = LoggerFactory.getLogger(NewsAnnouncementServiceImpl.class);
    
    @Autowired
    private NewsMapper newsMapper;
    
    @Override
    @Transactional
    public boolean addNews(News news) {
        try {
            // 设置默认值
            if (news.getPublishDate() == null) {
                news.setPublishDate(new Date());
            }
            if (news.getPublishStatus() == null) {
                news.setPublishStatus("Draft");
            }
            if (news.getNewsType() == null) {
                news.setNewsType("REGULAR_NEWS");
            }
            if (news.getIsNotice() == null) {
                news.setIsNotice(0);
            }
            
            int result = newsMapper.insertNews(news);
            logger.info("新增新闻成功，ID: {}", news.getId());
            return result > 0;
        } catch (Exception e) {
            logger.error("添加新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean updateNews(News news) {
        try {
            int result = newsMapper.updateNews(news);
            logger.info("更新新闻成功，ID: {}", news.getId());
            return result > 0;
        } catch (Exception e) {
            logger.error("更新新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean deleteNews(Long id) {
        try {
            int result = newsMapper.deleteNews(id);
            logger.info("删除新闻成功，ID: {}", id);
            return result > 0;
        } catch (Exception e) {
            logger.error("删除新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<News> getNewsList(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return newsMapper.selectNewsByPage(offset, pageSize);
    }
    
    @Override
    public List<News> getNewsWithPagination(int page, int pageSize, Map<String, Object> condition) {
        try {
            int offset = (page - 1) * pageSize;
            
            // 构建查询条件
            Map<String, Object> queryCondition = new HashMap<>();
            if (condition != null) {
                queryCondition.putAll(condition);
            }
            queryCondition.put("offset", offset);
            queryCondition.put("limit", pageSize);
            
            // 默认排序
            if (!queryCondition.containsKey("orderBy")) {
                queryCondition.put("orderBy", "publish_date DESC");
            }
            
            List<News> newsList = newsMapper.selectNewsByCondition(queryCondition);
            logger.info("分页查询新闻成功，页数: {}, 每页大小: {}, 结果数量: {}", page, pageSize, newsList.size());
            return newsList;
        } catch (Exception e) {
            logger.error("分页查询新闻失败: {}", e.getMessage());
            throw new RuntimeException("查询新闻列表失败: " + e.getMessage());
        }
    }

    // 添加一个新方法来获取总数量
    public int countNewsByCondition(Map<String, Object> condition) {
        try {
            return newsMapper.countByCondition(condition);
        } catch (Exception e) {
            logger.error("统计新闻数量失败: {}", e.getMessage());
            return 0;
        }
    }
    
    @Override
    public List<News> getNoticeList() {
        return newsMapper.selectAllNotices();
    }
    
    @Override
    public News getNewsDetail(Long id) {
        try {
            News news = newsMapper.selectById(id);
            if (news != null) {
                logger.info("获取新闻详情成功，ID: {}", id);
            } else {
                logger.warn("新闻不存在，ID: {}", id);
            }
            return news;
        } catch (Exception e) {
            logger.error("获取新闻详情失败，ID: {}, 错误: {}", id, e.getMessage());
            throw new RuntimeException("获取新闻详情失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean publishNews(Long id) {
        try {
            News news = newsMapper.selectById(id);
            if (news != null) {
                news.setPublishStatus("Published");
                news.setPublishDate(new Date());
                int result = newsMapper.updateNews(news);
                logger.info("发布新闻成功，ID: {}", id);
                return result > 0;
            }
            logger.warn("新闻不存在，无法发布，ID: {}", id);
            return false;
        } catch (Exception e) {
            logger.error("发布新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean scheduleNews(Long id, Date scheduleTime) {
        try {
            News news = newsMapper.selectById(id);
            if (news != null) {
                news.setPublishStatus("Scheduled");
                news.setScheduleTime(scheduleTime);
                int result = newsMapper.updateNews(news);
                logger.info("定时发布新闻成功，ID: {}, 发布时间: {}", id, scheduleTime);
                return result > 0;
            }
            return false;
        } catch (Exception e) {
            logger.error("定时发布新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    // ========== 新增方法 ==========
    
    @Override
    public Map<String, Object> getStatistics() {
        try {
            return newsMapper.getStatistics();
        } catch (Exception e) {
            logger.error("获取统计数据失败: {}", e.getMessage());
            Map<String, Object> empty = new HashMap<>();
            empty.put("total", 0);
            empty.put("published", 0);
            empty.put("draft", 0);
            empty.put("scheduled", 0);
            return empty;
        }
    }
    
    @Override
    public List<News> getAllNews() {
        try {
            return newsMapper.selectAllNews();
        } catch (Exception e) {
            logger.error("获取所有新闻失败: {}", e.getMessage());
            throw new RuntimeException("获取新闻列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<News> searchNews(String keyword) {
        try {
            Map<String, Object> condition = new HashMap<>();
            condition.put("keyword", keyword);
            List<News> results = newsMapper.selectNewsByCondition(condition);
            logger.info("搜索新闻成功，关键词: {}, 结果数量: {}", keyword, results.size());
            return results;
        } catch (Exception e) {
            logger.error("搜索新闻失败: {}", e.getMessage());
            throw new RuntimeException("搜索新闻失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<News> filterNews(Map<String, Object> filters) {
        try {
            List<News> results = newsMapper.selectNewsByCondition(filters);
            logger.info("筛选新闻成功，筛选条件: {}, 结果数量: {}", filters, results.size());
            return results;
        } catch (Exception e) {
            logger.error("筛选新闻失败: {}", e.getMessage());
            throw new RuntimeException("筛选新闻失败: " + e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public boolean batchDeleteNews(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return false;
            }
            int result = newsMapper.batchDeleteNews(ids);
            logger.info("批量删除新闻成功，删除数量: {}", result);
            return result > 0;
        } catch (Exception e) {
            logger.error("批量删除新闻失败: {}", e.getMessage());
            return false;
        }
    }
    
    
}
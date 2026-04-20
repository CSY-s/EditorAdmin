// src/main/java/com/bjfu/paper/service/editorialOffice/JournalAnnouncementServiceImpl.java
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.mapper.JournalAnnouncementMapper;
import com.bjfu.paper.model.entity.JournalAnnouncement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class JournalAnnouncementServiceImpl implements JournalAnnouncementService {
    
    private static final Logger logger = LoggerFactory.getLogger(JournalAnnouncementServiceImpl.class);
    
    @Autowired
    private JournalAnnouncementMapper journalAnnouncementMapper;
    
    @Override
    @Transactional
    public boolean addAnnouncement(JournalAnnouncement announcement) {
        try {
            // 设置默认值
            if (announcement.getPublishDate() == null) {
                announcement.setPublishDate(new Date());
            }
            if (announcement.getPublishStatus() == null) {
                announcement.setPublishStatus("Draft");
            }
            if (announcement.getIsTop() == null) {
                announcement.setIsTop(0);
            }
            if (announcement.getPriority() == null) {
                announcement.setPriority(3);
            }
            if (announcement.getViewCount() == null) {
                announcement.setViewCount(0);
            }
            
            int result = journalAnnouncementMapper.insert(announcement);
            logger.info("新增期刊公告成功，ID: {}", announcement.getId());
            return result > 0;
        } catch (Exception e) {
            logger.error("新增期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean updateAnnouncement(JournalAnnouncement announcement) {
        try {
            int result = journalAnnouncementMapper.updateById(announcement);
            logger.info("更新期刊公告成功，ID: {}", announcement.getId());
            return result > 0;
        } catch (Exception e) {
            logger.error("更新期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean deleteAnnouncement(Long id) {
        try {
            int result = journalAnnouncementMapper.deleteById(id);
            logger.info("删除期刊公告成功，ID: {}", id);
            return result > 0;
        } catch (Exception e) {
            logger.error("删除期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean batchDeleteAnnouncements(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return false;
            }
            int result = journalAnnouncementMapper.batchDelete(ids);
            logger.info("批量删除期刊公告成功，删除数量: {}", result);
            return result > 0;
        } catch (Exception e) {
            logger.error("批量删除期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public List<JournalAnnouncement> getAnnouncementList(int page, int pageSize, Map<String, Object> condition) {
        try {
            int offset = (page - 1) * pageSize;
            
            Map<String, Object> queryCondition = new HashMap<>();
            if (condition != null) {
                queryCondition.putAll(condition);
            }
            queryCondition.put("offset", offset);
            queryCondition.put("limit", pageSize);
            
            // 默认排序
            if (!queryCondition.containsKey("orderBy")) {
                queryCondition.put("orderBy", "is_top DESC, priority DESC, publish_date DESC");
            }
            
            List<JournalAnnouncement> announcements = journalAnnouncementMapper.selectAnnouncementsByCondition(queryCondition);
            logger.info("查询期刊公告列表成功，页数: {}, 每页大小: {}, 结果数量: {}", page, pageSize, announcements.size());
            return announcements;
        } catch (Exception e) {
            logger.error("查询期刊公告列表失败: {}", e.getMessage(), e);
            throw new RuntimeException("查询公告列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public int countAnnouncements(Map<String, Object> condition) {
        try {
            return journalAnnouncementMapper.countByCondition(condition);
        } catch (Exception e) {
            logger.error("统计期刊公告数量失败: {}", e.getMessage());
            return 0;
        }
    }
    
    @Override
    public JournalAnnouncement getAnnouncementDetail(Long id) {
        try {
            JournalAnnouncement announcement = journalAnnouncementMapper.selectAnnouncementDetail(id);
            if (announcement != null) {
                // 增加查看次数
                increaseViewCount(id, 1);
                logger.info("获取期刊公告详情成功，ID: {}", id);
            } else {
                logger.warn("期刊公告不存在，ID: {}", id);
            }
            return announcement;
        } catch (Exception e) {
            logger.error("获取期刊公告详情失败，ID: {}, 错误: {}", id, e.getMessage(), e);
            throw new RuntimeException("获取公告详情失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<JournalAnnouncement> getTopAnnouncements() {
        try {
            List<JournalAnnouncement> announcements = journalAnnouncementMapper.selectTopAnnouncements();
            logger.info("获取置顶公告成功，数量: {}", announcements.size());
            return announcements;
        } catch (Exception e) {
            logger.error("获取置顶公告失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    @Transactional
    public boolean publishAnnouncement(Long id) {
        try {
            JournalAnnouncement announcement = journalAnnouncementMapper.selectById(id);
            if (announcement != null) {
                announcement.setPublishStatus("Published");
                announcement.setPublishDate(new Date());
                int result = journalAnnouncementMapper.updateById(announcement);
                logger.info("发布期刊公告成功，ID: {}", id);
                return result > 0;
            }
            logger.warn("期刊公告不存在，无法发布，ID: {}", id);
            return false;
        } catch (Exception e) {
            logger.error("发布期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean scheduleAnnouncement(Long id, Date scheduleTime) {
        try {
            JournalAnnouncement announcement = journalAnnouncementMapper.selectById(id);
            if (announcement != null) {
                announcement.setPublishStatus("Scheduled");
                announcement.setScheduleTime(scheduleTime);
                int result = journalAnnouncementMapper.updateById(announcement);
                logger.info("定时发布期刊公告成功，ID: {}, 发布时间: {}", id, scheduleTime);
                return result > 0;
            }
            return false;
        } catch (Exception e) {
            logger.error("定时发布期刊公告失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean toggleTopStatus(Long id, Integer isTop) {
        try {
            int result = journalAnnouncementMapper.updateTopStatus(id, isTop);
            logger.info("切换置顶状态成功，ID: {}, 置顶状态: {}", id, isTop);
            return result > 0;
        } catch (Exception e) {
            logger.error("切换置顶状态失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean increaseViewCount(Long id, Integer count) {
        try {
            int result = journalAnnouncementMapper.updateViewCount(id, count);
            return result > 0;
        } catch (Exception e) {
            logger.error("增加查看次数失败: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public Map<String, Object> getStatistics() {
        try {
            return journalAnnouncementMapper.getStatistics();
        } catch (Exception e) {
            logger.error("获取期刊公告统计数据失败: {}", e.getMessage());
            return getEmptyStatistics();
        }
    }
    
    @Override
    @Transactional
    public boolean batchUpdateStatus(List<Long> ids, String status) {
        try {
            if (ids == null || ids.isEmpty()) {
                return false;
            }
            int result = journalAnnouncementMapper.batchUpdateStatus(ids, status);
            logger.info("批量更新状态成功，更新数量: {}, 新状态: {}", result, status);
            return result > 0;
        } catch (Exception e) {
            logger.error("批量更新状态失败: {}", e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public Map<String, Long> getTypeStatistics() {
        try {
            Map<String, Long> typeStats = new HashMap<>();
            
            // 先获取数据库中的所有类型统计
            List<Map<String, Object>> resultList = journalAnnouncementMapper.getTypeStatistics();
            
            if (resultList != null && !resultList.isEmpty()) {
                for (Map<String, Object> row : resultList) {
                    String type = (String) row.get("type");
                    Long count = 0L;
                    
                    Object countObj = row.get("count");
                    if (countObj != null) {
                        if (countObj instanceof Number) {
                            count = ((Number) countObj).longValue();
                        } else if (countObj instanceof String) {
                            try {
                                count = Long.parseLong((String) countObj);
                            } catch (NumberFormatException e) {
                                logger.warn("无法转换count值: {}", countObj);
                            }
                        }
                    }
                    
                    typeStats.put(type, count);
                }
            }
            
            // 确保所有类型都有默认值（即使数据库中没有该类型的记录）
            ensureAllTypes(typeStats);
            
            logger.info("获取类型统计成功: {}", typeStats);
            return typeStats;
            
        } catch (Exception e) {
            logger.error("获取类型统计失败: {}", e.getMessage());
            return getDefaultTypeStatistics();
        }
    }
    
    /**
     * 确保所有公告类型都有值
     */
    private void ensureAllTypes(Map<String, Long> typeStats) {
        // 定义所有可能的公告类型
        String[] allTypes = {
            "JOURNAL_INFO", 
            "IMPACT_FACTOR", 
            "POLICY_GUIDE", 
            "IMPORTANT_NOTICE", 
            "OTHER"
        };
        
        for (String type : allTypes) {
            typeStats.putIfAbsent(type, 0L);
        }
    }
    
    /**
     * 获取默认的类型统计数据（全为0）
     */
    private Map<String, Long> getDefaultTypeStatistics() {
        Map<String, Long> defaultStats = new HashMap<>();
        defaultStats.put("JOURNAL_INFO", 0L);
        defaultStats.put("IMPACT_FACTOR", 0L);
        defaultStats.put("POLICY_GUIDE", 0L);
        defaultStats.put("IMPORTANT_NOTICE", 0L);
        defaultStats.put("OTHER", 0L);
        return defaultStats;
    }
    
    @Override
    public List<JournalAnnouncement> getActiveAnnouncements(int limit) {
        try {
            Map<String, Object> condition = new HashMap<>();
            condition.put("publishStatus", "Published");
            condition.put("effectiveStatus", "active");
            condition.put("limit", limit);
            condition.put("orderBy", "priority DESC, publish_date DESC");
            
            return journalAnnouncementMapper.selectAnnouncementsByCondition(condition);
        } catch (Exception e) {
            logger.error("获取有效公告失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<JournalAnnouncement> getLatestAnnouncements(int limit) {
        try {
            Map<String, Object> condition = new HashMap<>();
            condition.put("publishStatus", "Published");
            condition.put("limit", limit);
            condition.put("orderBy", "publish_date DESC");
            
            return journalAnnouncementMapper.selectAnnouncementsByCondition(condition);
        } catch (Exception e) {
            logger.error("获取最新公告失败: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    private Map<String, Object> getEmptyStatistics() {
        Map<String, Object> emptyStats = new HashMap<>();
        emptyStats.put("total", 0);
        emptyStats.put("published", 0);
        emptyStats.put("draft", 0);
        emptyStats.put("scheduled", 0);
        emptyStats.put("topCount", 0);
        emptyStats.put("activeCount", 0);
        return emptyStats;
    }
}
// src/main/java/com/bjfu/paper/service/editorialOffice/JournalAnnouncementService.java
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.model.entity.JournalAnnouncement;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JournalAnnouncementService {
    
    // 新增公告
    boolean addAnnouncement(JournalAnnouncement announcement);
    
    // 更新公告
    boolean updateAnnouncement(JournalAnnouncement announcement);
    
    // 删除公告
    boolean deleteAnnouncement(Long id);
    
    // 批量删除
    boolean batchDeleteAnnouncements(List<Long> ids);
    
    // 获取公告列表（带分页和条件）
    List<JournalAnnouncement> getAnnouncementList(int page, int pageSize, Map<String, Object> condition);
    
    // 统计公告数量
    int countAnnouncements(Map<String, Object> condition);
    
    // 获取公告详情
    JournalAnnouncement getAnnouncementDetail(Long id);
    
    // 获取置顶公告
    List<JournalAnnouncement> getTopAnnouncements();
    
    // 发布公告
    boolean publishAnnouncement(Long id);
    
    // 定时发布
    boolean scheduleAnnouncement(Long id, Date scheduleTime);
    
    // 切换置顶状态
    boolean toggleTopStatus(Long id, Integer isTop);
    
    // 增加查看次数
    boolean increaseViewCount(Long id, Integer count);
    
    // 获取统计数据
    Map<String, Object> getStatistics();
    
    // 批量更新状态
    boolean batchUpdateStatus(List<Long> ids, String status);
    
    // 获取类型统计
    Map<String, Long> getTypeStatistics();
    
    // 获取有效公告列表
    List<JournalAnnouncement> getActiveAnnouncements(int limit);
    
    // 获取最新公告
    List<JournalAnnouncement> getLatestAnnouncements(int limit);
}
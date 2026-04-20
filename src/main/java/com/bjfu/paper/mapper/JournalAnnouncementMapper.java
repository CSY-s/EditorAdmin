// src/main/java/com/bjfu/paper/mapper/JournalAnnouncementMapper.java
package com.bjfu.paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjfu.paper.model.entity.JournalAnnouncement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface JournalAnnouncementMapper extends BaseMapper<JournalAnnouncement> {
    
    /**
     * 条件查询公告列表（带分页）
     */
    List<JournalAnnouncement> selectAnnouncementsByCondition(Map<String, Object> condition);
    
    /**
     * 统计条件查询数量
     */
    int countByCondition(Map<String, Object> condition);
    
    /**
     * 获取置顶公告列表
     */
    List<JournalAnnouncement> selectTopAnnouncements();
    
    /**
     * 根据ID获取公告详情（包含发布人姓名和期刊名称）
     */
    JournalAnnouncement selectAnnouncementDetail(@Param("id") Long id);
    
    /**
     * 更新查看次数
     */
    int updateViewCount(@Param("id") Long id, @Param("count") Integer count);
    
    /**
     * 批量更新状态
     */
    int batchUpdateStatus(@Param("ids") List<Long> ids, @Param("status") String status);
    
    /**
     * 批量删除
     */
    int batchDelete(@Param("ids") List<Long> ids);
    
    /**
     * 更新置顶状态
     */
    int updateTopStatus(@Param("id") Long id, @Param("isTop") Integer isTop);
    
    /**
     * 获取统计数据
     */
    Map<String, Object> getStatistics();
    
    /**
     * 获取有效公告数量
     */
    int countActiveAnnouncements();
    
    /**
     * 根据类型统计数量 - 改为返回 List<Map<String, Object>>
     */
    List<Map<String, Object>> countByType();
    
    /**
     * 获取类型统计 - 改为返回 List<Map<String, Object>>
     */
    List<Map<String, Object>> getTypeStatistics();
}
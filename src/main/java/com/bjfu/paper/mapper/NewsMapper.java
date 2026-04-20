// NewsMapper.java
package com.bjfu.paper.mapper;

import com.bjfu.paper.model.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapper {
    
    // 新增新闻
    int insertNews(News news);
    
    // 根据ID查询新闻
    News selectById(@Param("id") Long id);
    
    // 更新新闻
    int updateNews(News news);
    
    // 删除新闻
    int deleteNews(@Param("id") Long id);
    
    // 分页查询新闻
    List<News> selectNewsByPage(
        @Param("offset") Integer offset, 
        @Param("limit") Integer limit
    );
    
    // 查询所有公告
    List<News> selectAllNotices();
    
    // 查询所有新闻（不分页）
    List<News> selectAllNews();
    
    // 统计新闻数量
    int countNews();
    
    // 条件查询新闻（支持分页）
    List<News> selectNewsByCondition(Map<String, Object> condition);
    
    // 条件统计数量
    int countByCondition(Map<String, Object> condition);
    
    // 获取统计数据
    Map<String, Object> getStatistics();
    
    // 批量删除
    int batchDeleteNews(@Param("ids") List<Long> ids);
}
// src/main/java/com/bjfu/paper/mapper/ManuscriptMapper.java
package com.bjfu.paper.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjfu.paper.model.entity.Manuscript;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ManuscriptMapper extends BaseMapper<Manuscript> {
    
    /**
     * 分页查询待审查稿件（带作者信息）
     */
    List<Manuscript> selectPendingManuscriptsByPage(@Param("offset") int offset,
                                                   @Param("pageSize") int pageSize,
                                                   @Param("keyword") String keyword);
    
    /**
     * 统计待审查稿件数量
     */
    int countPendingManuscripts(@Param("keyword") String keyword);
    
    /**
     * 获取稿件详情（包含作者信息）
     */
    Manuscript selectManuscriptDetail(@Param("msId") String msId);
    
    /**
     * 更新稿件状态
     */
    int updateStatus(@Param("msId") String msId, @Param("status") String status);
    
    /**
     * 批量更新状态
     */
    int batchUpdateStatus(@Param("msIds") List<String> msIds, @Param("status") String status);
    
    /**
     * 获取统计数据
     */
    int countByStatusPending();
    
    int countByStatusPassed();
    
    int countByStatusFailed();
    
    int countTotal();
    
    
    /**
     * 获取已发表的论文（状态为ACCEPTED且已发布）
     */
    List<Manuscript> selectPublishedPapers(@Param("limit") int limit);

    /**
     * 获取最新发表的论文
     */
    List<Manuscript> selectLatestPublishedPapers(@Param("limit") int limit);

    /**
     * 获取高被引论文
     */
    List<Manuscript> selectTopCitedPapers(@Param("limit") int limit);

    /**
     * 获取最多下载论文
     */
    List<Manuscript> selectTopDownloadedPapers(@Param("limit") int limit);

    /**
     * 获取最受欢迎论文（综合view_count和likes_count）
     */
    List<Manuscript> selectTopPopularPapers(@Param("limit") int limit);

    /**
     * 增加查看次数
     */
    int increaseViewCount(@Param("msId") String msId, @Param("increment") int increment);

    /**
     * 增加下载次数
     */
    int increaseDownloadCount(@Param("msId") String msId, @Param("increment") int increment);

    /**
     * 增加引用次数
     */
    int increaseCitationCount(@Param("msId") String msId, @Param("increment") int increment);

    /**
     * 增加点赞次数
     */
    int increaseLikesCount(@Param("msId") String msId, @Param("increment") int increment);
}
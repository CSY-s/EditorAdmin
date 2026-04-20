// src/main/java/com/bjfu/paper/service/editorialOffice/TechCheckService.java
package com.bjfu.paper.service.editorialOffice;

import com.bjfu.paper.model.entity.Manuscript;
import java.util.List;
import java.util.Map;

public interface TechCheckService {
    
    // 获取待审查稿件列表（使用Manuscript实体）
    List<Manuscript> getPendingManuscripts(int page, int pageSize, String keyword);
    
    // 统计待审查稿件数量
    int countPendingManuscripts(String keyword);
    
    // 获取稿件详情（使用Manuscript实体）
    Manuscript getManuscriptDetail(String msId);
    
    // 提交形式审查结果
    boolean submitTechCheck(String msId, String result, String comment, Map<String, Object> checkData);
    
    // 退回稿件给作者修改
    boolean unsubmitManuscript(String msId, String reason);
    
    // 批量审查通过
    boolean batchPass(List<String> msIds, String comment);
    
    // 获取统计数据
    Map<String, Object> getStatistics();
    
    // 新增：更新稿件信息
    boolean updateManuscript(Manuscript manuscript);
    
    // 新增：根据ID获取稿件（基础信息）
    Manuscript getManuscriptById(String msId);

    // 新增：执行完整的稿件审查
    Map<String, Object> performCompleteCheck(String msId);
    
    // 新增：获取审查历史
    List<Map<String, Object>> getCheckHistory(String msId);
}
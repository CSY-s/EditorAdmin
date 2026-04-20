// src/main/java/com/bjfu/paper/controller/editorialOffice/TechCheckController.java
package com.bjfu.paper.controller.editorialOffice;

import com.bjfu.paper.model.dto.Result;
import com.bjfu.paper.model.entity.Manuscript;
import com.bjfu.paper.service.editorialOffice.TechCheckService;
import com.bjfu.paper.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections; 
@RestController
@RequestMapping("/editorial-office/tech-check")
public class TechCheckController {
    
    @Autowired
    private TechCheckService techCheckService;
    
    /**
     * 获取待审查稿件列表（支持分页）
     */
    @GetMapping("/list")
    public Result getPendingManuscripts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword) {
        try {
            List<Manuscript> manuscripts = techCheckService.getPendingManuscripts(page, pageSize, keyword);
            int total = techCheckService.countPendingManuscripts(keyword);
            
            Map<String, Object> result = new HashMap<>();
            result.put("list", manuscripts);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("totalPages", (int) Math.ceil((double) total / pageSize));
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取稿件列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取稿件详情
     */
    @GetMapping("/detail/{msId}")
    public Result getManuscriptDetail(@PathVariable String msId) {
        try {
            Manuscript manuscript = techCheckService.getManuscriptDetail(msId);
            if (manuscript != null) {
                return ResultUtil.success(manuscript);
            } else {
                return ResultUtil.error("稿件不存在");
            }
        } catch (Exception e) {
            return ResultUtil.error("获取稿件详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取稿件基本信息（不含关联信息）
     */
    @GetMapping("/basic/{msId}")
    public Result getManuscriptBasic(@PathVariable String msId) {
        try {
            Manuscript manuscript = techCheckService.getManuscriptById(msId);
            if (manuscript != null) {
                return ResultUtil.success(manuscript);
            } else {
                return ResultUtil.error("稿件不存在");
            }
        } catch (Exception e) {
            return ResultUtil.error("获取稿件信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 提交形式审查结果
     */
    @PostMapping("/submit")
    public Result submitTechCheck(@RequestBody Map<String, Object> checkData) {
        try {
            String msId = (String) checkData.get("msId");
            String result = (String) checkData.get("result"); // pass/fail/reject
            String comment = (String) checkData.get("comment");
            
            boolean success = techCheckService.submitTechCheck(msId, result, comment, checkData);
            if (success) {
                return ResultUtil.success("审查结果提交成功");
            } else {
                return ResultUtil.error("提交失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("提交审查结果失败: " + e.getMessage());
        }
    }
    
    /**
     * 退回稿件给作者修改
     */
    @PostMapping("/unsubmit/{msId}")
    public Result unsubmitManuscript(@PathVariable String msId,
                                     @RequestBody Map<String, String> request) {
        try {
            String reason = request.get("reason");
            boolean success = techCheckService.unsubmitManuscript(msId, reason);
            if (success) {
                return ResultUtil.success("稿件已退回给作者修改");
            } else {
                return ResultUtil.error("退回失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("退回稿件失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量审查通过
     */
    @PostMapping("/batch-pass")
    public Result batchPass(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> msIds = (List<String>) request.get("msIds");
            String comment = (String) request.get("comment");
            
            boolean success = techCheckService.batchPass(msIds, comment);
            if (success) {
                return ResultUtil.success("批量通过成功");
            } else {
                return ResultUtil.error("批量操作失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("批量操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新稿件信息
     */
    @PostMapping("/update")
    public Result updateManuscript(@RequestBody Manuscript manuscript) {
        try {
            boolean success = techCheckService.updateManuscript(manuscript);
            if (success) {
                return ResultUtil.success("稿件信息更新成功");
            } else {
                return ResultUtil.error("更新失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("更新稿件失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取统计数据
     */
    @GetMapping("/stats")
    public Result getStatistics() {
        try {
            Map<String, Object> stats = techCheckService.getStatistics();
            return ResultUtil.success(stats);
        } catch (Exception e) {
            return ResultUtil.error("获取统计数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 执行自动检查
     */
    @GetMapping("/auto-check/{msId}")
    public Result performAutoCheck(@PathVariable String msId) {
        try {
            Map<String, Object> checkResult = techCheckService.performCompleteCheck(msId);
            return ResultUtil.success(checkResult);
        } catch (Exception e) {
            return ResultUtil.error("自动检查失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取审查历史
     */
    @GetMapping("/history/{msId}")
    public Result getCheckHistory(@PathVariable String msId) {
        try {
            List<Map<String, Object>> history = techCheckService.getCheckHistory(msId);
            return ResultUtil.success(history);
        } catch (Exception e) {
            return ResultUtil.error("获取审查历史失败: " + e.getMessage());
        }
    }
    
    /**
     * 批量检查
     */
    @PostMapping("/batch-check")
    public Result batchCheck(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<String> msIds = (List<String>) request.get("msIds");
            Map<String, Object> results = new HashMap<>();
            
            for (String msId : msIds) {
                try {
                    Map<String, Object> checkResult = techCheckService.performCompleteCheck(msId);
                    results.put(msId, checkResult);
                } catch (Exception e) {
                    results.put(msId, Collections.singletonMap("error", e.getMessage()));
                }
            }
            
            return ResultUtil.success(results);
        } catch (Exception e) {
            return ResultUtil.error("批量检查失败: " + e.getMessage());
        }
    }
}
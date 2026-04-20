// src/main/java/com/bjfu/paper/controller/editorialOffice/JournalAnnouncementController.java
package com.bjfu.paper.controller.editorialOffice;

import com.bjfu.paper.model.dto.Result;
import com.bjfu.paper.model.entity.JournalAnnouncement;
import com.bjfu.paper.service.editorialOffice.JournalAnnouncementService;
import com.bjfu.paper.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/editorial-office/journal-announcement")
public class JournalAnnouncementController {
    
    @Autowired
    private JournalAnnouncementService journalAnnouncementService;
    
    /**
     * 新增公告
     */
    @PostMapping("/add")
    public Result addAnnouncement(@RequestBody JournalAnnouncement announcement, HttpSession session) {
        try {
            // 从session获取当前用户ID
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                // 测试时默认使用管理员ID
                userId = 3L; // 编辑李四的ID
            }
            
            announcement.setPublisherId(userId);
            boolean success = journalAnnouncementService.addAnnouncement(announcement);
            if (success) {
                return ResultUtil.success("添加成功");
            } else {
                return ResultUtil.error("添加失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("添加公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 更新公告
     */
    @PostMapping("/update")
    public Result updateAnnouncement(@RequestBody JournalAnnouncement announcement) {
        try {
            boolean success = journalAnnouncementService.updateAnnouncement(announcement);
            if (success) {
                return ResultUtil.success("更新成功");
            } else {
                return ResultUtil.error("更新失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("更新公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 删除公告
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteAnnouncement(@PathVariable Long id) {
        try {
            boolean success = journalAnnouncementService.deleteAnnouncement(id);
            if (success) {
                return ResultUtil.success("删除成功");
            } else {
                return ResultUtil.error("删除失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("删除公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除
     */
    @PostMapping("/batch-delete")
    public Result batchDeleteAnnouncements(@RequestBody Map<String, List<Long>> request) {
        try {
            List<Long> ids = request.get("ids");
            boolean success = journalAnnouncementService.batchDeleteAnnouncements(ids);
            if (success) {
                return ResultUtil.success("批量删除成功");
            } else {
                return ResultUtil.error("批量删除失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("批量删除错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取公告列表（分页+条件）
     */
    @GetMapping("/list")
    public Result getAnnouncementList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String journal,
            @RequestParam(required = false) String top,
            @RequestParam(required = false) String effective,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            Map<String, Object> condition = new HashMap<>();
            
            // 构建查询条件
            if (keyword != null && !keyword.isEmpty()) {
                condition.put("keyword", keyword);
            }
            if (type != null && !type.isEmpty()) {
                condition.put("announcementType", type);
            }
            if (status != null && !status.isEmpty()) {
                condition.put("publishStatus", status);
            }
            if (priority != null && !priority.isEmpty()) {
                condition.put("priority", Integer.parseInt(priority));
            }
            if (journal != null && !journal.isEmpty()) {
                condition.put("relatedJournalId", Long.parseLong(journal));
            }
            if (top != null && !top.isEmpty()) {
                condition.put("isTop", Integer.parseInt(top));
            }
            if (effective != null && !effective.isEmpty()) {
                condition.put("effectiveStatus", effective);
            }
            if (startDate != null && !startDate.isEmpty()) {
                condition.put("startDate", startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                condition.put("endDate", endDate);
            }
            
            // 获取分页数据
            List<JournalAnnouncement> announcements = journalAnnouncementService.getAnnouncementList(page, pageSize, condition);
            
            // 获取总数
            int total = journalAnnouncementService.countAnnouncements(condition);
            
            Map<String, Object> result = new HashMap<>();
            result.put("data", announcements);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("totalPages", (int) Math.ceil((double) total / pageSize));
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取公告列表错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取公告详情
     */
    @GetMapping("/detail/{id}")
    public Result getAnnouncementDetail(@PathVariable Long id) {
        try {
            JournalAnnouncement announcement = journalAnnouncementService.getAnnouncementDetail(id);
            if (announcement != null) {
                return ResultUtil.success(announcement);
            } else {
                return ResultUtil.error("公告不存在");
            }
        } catch (Exception e) {
            return ResultUtil.error("获取公告详情错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取置顶公告
     */
    @GetMapping("/top")
    public Result getTopAnnouncements() {
        try {
            List<JournalAnnouncement> announcements = journalAnnouncementService.getTopAnnouncements();
            return ResultUtil.success(announcements);
        } catch (Exception e) {
            return ResultUtil.error("获取置顶公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 发布公告
     */
    @PostMapping("/publish/{id}")
    public Result publishAnnouncement(@PathVariable Long id) {
        try {
            boolean success = journalAnnouncementService.publishAnnouncement(id);
            if (success) {
                return ResultUtil.success("发布成功");
            } else {
                return ResultUtil.error("发布失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("发布公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 定时发布
     */
    @PostMapping("/schedule/{id}")
    public Result scheduleAnnouncement(@PathVariable Long id, 
                                       @RequestParam String scheduleTimeStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date scheduleTime = sdf.parse(scheduleTimeStr);
            
            boolean success = journalAnnouncementService.scheduleAnnouncement(id, scheduleTime);
            if (success) {
                return ResultUtil.success("定时发布设置成功");
            } else {
                return ResultUtil.error("设置失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("定时发布设置错误: " + e.getMessage());
        }
    }
    
    /**
     * 切换置顶状态
     */
    @PostMapping("/toggle-top/{id}")
    public Result toggleTopStatus(@PathVariable Long id,
                                  @RequestParam Integer isTop) {
        try {
            boolean success = journalAnnouncementService.toggleTopStatus(id, isTop);
            if (success) {
                String message = isTop == 1 ? "置顶成功" : "取消置顶成功";
                return ResultUtil.success(message);
            } else {
                return ResultUtil.error("操作失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("切换置顶状态错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取统计数据
     */
    @GetMapping("/stats")
    public Result getStatistics() {
        try {
            Map<String, Object> stats = journalAnnouncementService.getStatistics();
            return ResultUtil.success(stats);
        } catch (Exception e) {
            return ResultUtil.error("获取统计数据错误: " + e.getMessage());
        }
    }
    
    /**
     * 批量更新状态
     */
    @PostMapping("/batch-update-status")
    public Result batchUpdateStatus(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) request.get("ids");
            String status = (String) request.get("status");
            
            boolean success = journalAnnouncementService.batchUpdateStatus(ids, status);
            if (success) {
                return ResultUtil.success("批量更新状态成功");
            } else {
                return ResultUtil.error("批量更新失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("批量更新状态错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取类型统计
     */
    @GetMapping("/type-stats")
    public Result getTypeStatistics() {
        try {
            Map<String, Long> typeStats = journalAnnouncementService.getTypeStatistics();
            return ResultUtil.success(typeStats);
        } catch (Exception e) {
            return ResultUtil.error("获取类型统计错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取有效公告
     */
    @GetMapping("/active")
    public Result getActiveAnnouncements(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<JournalAnnouncement> announcements = journalAnnouncementService.getActiveAnnouncements(limit);
            return ResultUtil.success(announcements);
        } catch (Exception e) {
            return ResultUtil.error("获取有效公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取最新公告
     */
    @GetMapping("/latest")
    public Result getLatestAnnouncements(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<JournalAnnouncement> announcements = journalAnnouncementService.getLatestAnnouncements(limit);
            return ResultUtil.success(announcements);
        } catch (Exception e) {
            return ResultUtil.error("获取最新公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 搜索公告
     */
    @GetMapping("/search")
    public Result searchAnnouncements(@RequestParam String keyword,
                                      @RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int pageSize) {
        try {
            Map<String, Object> condition = new HashMap<>();
            condition.put("keyword", keyword);
            
            List<JournalAnnouncement> announcements = journalAnnouncementService.getAnnouncementList(page, pageSize, condition);
            int total = journalAnnouncementService.countAnnouncements(condition);
            
            Map<String, Object> result = new HashMap<>();
            result.put("data", announcements);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("搜索公告错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取首页公告数据
     */
    @GetMapping("/home-data")
    public Result getHomeData() {
        try {
            Map<String, Object> result = new HashMap<>();
            
            // 获取置顶公告
            List<JournalAnnouncement> topAnnouncements = journalAnnouncementService.getTopAnnouncements();
            result.put("topAnnouncements", topAnnouncements);
            
            // 获取最新公告
            List<JournalAnnouncement> latestAnnouncements = journalAnnouncementService.getLatestAnnouncements(5);
            result.put("latestAnnouncements", latestAnnouncements);
            
            // 获取统计数据
            Map<String, Object> stats = journalAnnouncementService.getStatistics();
            result.put("stats", stats);
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取首页数据错误: " + e.getMessage());
        }
    }
    
    
}
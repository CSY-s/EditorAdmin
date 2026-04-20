package com.bjfu.paper.controller.editorialOffice;

import com.bjfu.paper.model.entity.News;
import com.bjfu.paper.service.editorialOffice.NewsAnnouncementService;
import com.bjfu.paper.util.ResultUtil;
import com.bjfu.paper.model.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/editorial-office/news")
public class NewsAnnouncementController {
    
    @Autowired
    private NewsAnnouncementService newsAnnouncementService;
    
    /**
     * 新增新闻/公告
     */
    @PostMapping("/add")
    public Result addNews(@RequestBody News news, HttpSession session) {
        try {
            // 从session获取当前用户ID
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                // 测试时默认使用管理员ID
                userId = 3L; // 编辑李四的ID
            }
            
            news.setPublisherId(userId);
            boolean success = newsAnnouncementService.addNews(news);
            if (success) {
                return ResultUtil.success("添加成功");
            } else {
                return ResultUtil.error("添加失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("添加新闻错误: " + e.getMessage());
        }
    }
    
    /**
     * 更新新闻/公告
     */
    @PostMapping("/update")
    public Result updateNews(@RequestBody News news) {
        try {
            boolean success = newsAnnouncementService.updateNews(news);
            if (success) {
                return ResultUtil.success("更新成功");
            } else {
                return ResultUtil.error("更新失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("更新新闻错误: " + e.getMessage());
        }
    }
    
    /**
     * 删除新闻/公告
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteNews(@PathVariable Long id) {
        try {
            boolean success = newsAnnouncementService.deleteNews(id);
            if (success) {
                return ResultUtil.success("删除成功");
            } else {
                return ResultUtil.error("删除失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("删除新闻错误: " + e.getMessage());
        }
    }
    
    /**
     * 批量删除新闻
     */
    @PostMapping("/batch-delete")
    public Result batchDeleteNews(@RequestBody Map<String, List<Long>> request) {
        try {
            List<Long> ids = request.get("ids");
            boolean success = newsAnnouncementService.batchDeleteNews(ids);
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
     * 获取新闻列表（分页）
     */
    @GetMapping("/list")
    public Result getNewsList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int pageSize,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String notice) {
        try {
            Map<String, Object> condition = new HashMap<>();
            if (keyword != null && !keyword.isEmpty()) {
                condition.put("keyword", keyword);
            }
            if (status != null && !status.isEmpty()) {
                condition.put("publishStatus", status);
            }
            if (type != null && !type.isEmpty()) {
                condition.put("newsType", type);
            }
            if (notice != null && !notice.isEmpty()) {
                condition.put("isNotice", Integer.parseInt(notice));
            }
            
            // 获取分页数据
            List<News> newsList = newsAnnouncementService.getNewsWithPagination(page, pageSize, condition);
            
            // 获取总数
            int total = newsAnnouncementService.countNewsByCondition(condition);
            
            Map<String, Object> result = new HashMap<>();
            result.put("data", newsList);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("totalPages", (int) Math.ceil((double) total / pageSize));
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取新闻列表错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取所有新闻（不分页，用于下拉选择）
     */
    @GetMapping("/list-all")
    public Result getAllNews() {
        try {
            List<News> newsList = newsAnnouncementService.getAllNews();
            return ResultUtil.success(newsList);
        } catch (Exception e) {
            return ResultUtil.error("获取新闻列表错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取公告列表
     */
    @GetMapping("/notices")
    public Result getNoticeList() {
        try {
            List<News> noticeList = newsAnnouncementService.getNoticeList();
            return ResultUtil.success(noticeList);
        } catch (Exception e) {
            return ResultUtil.error("获取公告列表错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取新闻详情
     */
    @GetMapping("/detail/{id}")
    public Result getNewsDetail(@PathVariable Long id) {
        try {
            News news = newsAnnouncementService.getNewsDetail(id);
            if (news != null) {
                return ResultUtil.success(news);
            } else {
                return ResultUtil.error("新闻不存在");
            }
        } catch (Exception e) {
            return ResultUtil.error("获取新闻详情错误: " + e.getMessage());
        }
    }
    
    /**
     * 发布新闻
     */
    @PostMapping("/publish/{id}")
    public Result publishNews(@PathVariable Long id) {
        try {
            boolean success = newsAnnouncementService.publishNews(id);
            if (success) {
                return ResultUtil.success("发布成功");
            } else {
                return ResultUtil.error("发布失败");
            }
        } catch (Exception e) {
            return ResultUtil.error("发布新闻错误: " + e.getMessage());
        }
    }
    
    /**
     * 定时发布新闻
     */
    @PostMapping("/schedule/{id}")
    public Result scheduleNews(@PathVariable Long id, 
                               @RequestParam String scheduleTimeStr) {
        try {
            // 将字符串时间转换为Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date scheduleTime = sdf.parse(scheduleTimeStr);
            
            boolean success = newsAnnouncementService.scheduleNews(id, scheduleTime);
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
     * 获取统计数据
     */
    @GetMapping("/stats")
    public Result getStatistics() {
        try {
            Map<String, Object> stats = newsAnnouncementService.getStatistics();
            return ResultUtil.success(stats);
        } catch (Exception e) {
            return ResultUtil.error("获取统计数据错误: " + e.getMessage());
        }
    }
    
    /**
     * 搜索新闻
     */
    @GetMapping("/search")
    public Result searchNews(@RequestParam String keyword) {
        try {
            List<News> results = newsAnnouncementService.searchNews(keyword);
            return ResultUtil.success(results);
        } catch (Exception e) {
            return ResultUtil.error("搜索新闻错误: " + e.getMessage());
        }
    }
    
    /**
     * 筛选新闻
     */
    @GetMapping("/filter")
    public Result filterNews(@RequestParam(required = false) String type,
                            @RequestParam(required = false) String status,
                            @RequestParam(required = false) String notice,
                            @RequestParam(required = false) String dateRange) {
        try {
            Map<String, Object> filters = new HashMap<>();
            if (type != null && !type.isEmpty()) {
                filters.put("newsType", type);
            }
            if (status != null && !status.isEmpty()) {
                filters.put("publishStatus", status);
            }
            if (notice != null && !notice.isEmpty()) {
                filters.put("isNotice", Integer.parseInt(notice));
            }
            
            List<News> results = newsAnnouncementService.filterNews(filters);
            return ResultUtil.success(results);
        } catch (Exception e) {
            return ResultUtil.error("筛选新闻错误: " + e.getMessage());
        }
    }
    
    @GetMapping("/list-for-edit")
    public Result getNewsForEdit(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "5") int pageSize) {
        try {
            // 由于我们之前已经实现了分页查询，可以直接使用现有方法
            // 这里需要确保你的服务层支持带参数的查询
            Map<String, Object> condition = new HashMap<>();
            // 可以添加排序条件
            condition.put("orderBy", "publish_date DESC");
            
            // 模拟分页，实际应该从数据库分页查询
            List<News> allNews = newsAnnouncementService.getAllNews();
            
            // 手动分页（实际应该在数据库层面分页）
            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, allNews.size());
            
            List<News> pageData = allNews.subList(start, end);
            
            Map<String, Object> result = new HashMap<>();
            result.put("data", pageData);
            result.put("total", allNews.size());
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取编辑列表错误: " + e.getMessage());
        }
    }
    
    /**
     * 获取新闻列表（带分页和条件）
     */
    @GetMapping("/list-with-condition")
    public Result getNewsWithCondition(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int pageSize,
                                      @RequestParam(required = false) String keyword,
                                      @RequestParam(required = false) String status,
                                      @RequestParam(required = false) String type,
                                      @RequestParam(required = false) String notice) {
        try {
            Map<String, Object> condition = new HashMap<>();
            if (keyword != null && !keyword.isEmpty()) {
                condition.put("keyword", keyword);
            }
            if (status != null && !status.isEmpty()) {
                condition.put("publishStatus", status);
            }
            if (type != null && !type.isEmpty()) {
                condition.put("newsType", type);
            }
            if (notice != null && !notice.isEmpty()) {
                condition.put("isNotice", Integer.parseInt(notice));
            }
            
            List<News> newsList = newsAnnouncementService.getNewsWithPagination(page, pageSize, condition);
            
            // 获取总数用于分页
            Map<String, Object> countCondition = new HashMap<>(condition);
            List<News> allNews = newsAnnouncementService.filterNews(countCondition);
            int total = allNews.size();
            
            Map<String, Object> result = new HashMap<>();
            result.put("data", newsList);
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("totalPages", (int) Math.ceil((double) total / pageSize));
            
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("获取新闻列表错误: " + e.getMessage());
        }
    }
}
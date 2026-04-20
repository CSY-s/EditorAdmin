package com.bjfu.paper.controller;

import com.bjfu.paper.model.dto.Result;
import com.bjfu.paper.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DatabaseTestController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 测试数据库连接
     */
    @GetMapping("/db")
    public Result testDatabaseConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT '数据库连接成功!'", String.class);
            return ResultUtil.success(result);
        } catch (Exception e) {
            return ResultUtil.error("数据库连接失败: " + e.getMessage());
        }
    }
    
    /**
     * 测试新闻表是否存在
     */
    @GetMapping("/news-table")
    public Result testNewsTable() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM news", Integer.class);  // 修改
            return ResultUtil.success("新闻表存在，共" + count + "条记录");
        } catch (Exception e) {
            return ResultUtil.error("新闻表查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 测试用户表是否存在
     */
    public Result testUserTable() {
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Integer.class);  // 修改
            return ResultUtil.success("用户表存在，共" + count + "条记录");
        } catch (Exception e) {
            return ResultUtil.error("用户表查询失败: " + e.getMessage());
        }
    }
}
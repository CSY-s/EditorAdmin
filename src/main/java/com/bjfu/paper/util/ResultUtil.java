package com.bjfu.paper.util;

import com.bjfu.paper.model.dto.Result;

public class ResultUtil {
    
    // 成功
    public static Result success() {
        return new Result(200, "操作成功", null);
    }
    
    public static Result success(Object data) {
        return new Result(200, "操作成功", data);
    }
    
    public static Result success(String message, Object data) {
        return new Result(200, message, data);
    }
    
    // 错误
    public static Result error() {
        return new Result(500, "操作失败", null);
    }
    
    public static Result error(String message) {
        return new Result(500, message, null);
    }
    
    public static Result error(int code, String message) {
        return new Result(code, message, null);
    }
    
    // 未登录
    public static Result unLogin() {
        return new Result(401, "用户未登录", null);
    }
    
    // 无权限
    public static Result noPermission() {
        return new Result(403, "权限不足", null);
    }
    
    // 参数错误
    public static Result paramError() {
        return new Result(400, "参数错误", null);
    }
    
    public static Result paramError(String message) {
        return new Result(400, message, null);
    }
}
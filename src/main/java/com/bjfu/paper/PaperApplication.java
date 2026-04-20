package com.bjfu.paper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaperApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaperApplication.class, args);
        System.out.println("论文管理系统启动成功！");
    }
}
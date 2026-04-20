package com.bjfu.paper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/front/";
    }

    @GetMapping("/front")
    public String front() {
        return "redirect:/front/";
    }

    @GetMapping("/front/")
    public String frontIndex() {
        return "forward:/front/index.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/back/editorialOffice/dashboard.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/back/editorialOffice/dashboard.html";
    }

    @GetMapping("/journal-notice")
    public String journalNotice() {
        return "forward:/back/editorialOffice/journal_notice.html";
    }

    @GetMapping("/news-manage")
    public String newsManage() {
        return "forward:/back/editorialOffice/news_manage.html";
    }

    @GetMapping("/tech-check")
    public String techCheck() {
        return "forward:/back/editorialOffice/tech_check.html";
    }
}

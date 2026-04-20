package com.bjfu.paper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontRouteController {

    @GetMapping({
            "/front/{path:[^.]*}",
            "/front/{path:[^.]*}/{subPath:[^.]*}",
            "/front/{path:[^.]*}/{subPath:[^.]*}/{leaf:[^.]*}"
    })
    public String forwardFrontRoutes() {
        return "forward:/front/index.html";
    }
}

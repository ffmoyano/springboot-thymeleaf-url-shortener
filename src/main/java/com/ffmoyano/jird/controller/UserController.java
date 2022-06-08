package com.ffmoyano.jird.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

    @GetMapping({ "/links"})
    public String links() {
        return "links";
    }

    @PostMapping({"/links"})
    public String insert(@RequestBody String url) {
        return "links";
    }

}

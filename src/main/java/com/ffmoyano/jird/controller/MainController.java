package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.service.VerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    private final VerificationService verificationService;

    public MainController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/user/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(verificationService.isAuthenticated()) {
            return redirectIndex();
        } else {
            return "login";
        }

    }

    @GetMapping({ "/user/index"})
    public String index() {
        return "index";
    }
}

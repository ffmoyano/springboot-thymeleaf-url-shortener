package com.ffmoyano.urlshortener.controller;

import com.ffmoyano.urlshortener.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping( "/login")
    public String login() {
        if(userService.isUserAuthenticated()) {
            return "redirect:/link/";
        } else {
            return "login";
        }

    }


}

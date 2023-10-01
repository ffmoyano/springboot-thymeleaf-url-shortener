package com.ffmoyano.urlshortener.controller;

import com.ffmoyano.urlshortener.dto.UserDto;
import com.ffmoyano.urlshortener.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return userService.isUserAuthenticated() ? "redirect:/link/" : "login";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserDto user, Model model, HttpServletRequest request) {
        if (!userService.userExists(user.email()) && StringUtils.isNotEmpty(user.password())) {
            userService.signup(user);
        }
        return "login";
    }
}

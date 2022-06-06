package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;


@Controller
public class MainController {

    private final VerificationService verificationService;

    public MainController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }



    @GetMapping("/{shortUrl}")
    public ResponseEntity redirect(@PathVariable(value = "shortUrl") String shortUrl) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://www.yahoo.com"))
                .build();
    }

    @GetMapping({"", "/login"})
    public String login(Model model) {
        if(verificationService.isAuthenticated()) {
            return index();
        } else {
            return "login";
        }

    }

    @GetMapping({ "/user"})
    public String index() {
        return "index";
    }
}

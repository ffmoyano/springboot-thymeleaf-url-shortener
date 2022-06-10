package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.entity.Link;
import com.ffmoyano.jird.service.LinkService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UrlValidator urlValidator;
    private final LinkService linkService;

    public UserController(UrlValidator urlValidator, LinkService linkService) {
        this.urlValidator = urlValidator;
        this.linkService = linkService;
    }

    @GetMapping({ "/links"})
    public String links() {
        return "links";
    }

    @PostMapping({"/links"})
    public String insert(@RequestBody String url, Model model) {
        boolean isUrlValid = linkService.checkIsValid(url);
        if(isUrlValid) {
            Link link = linkService.createLinkFromUrl(url);
            linkService.save(link);
        } else {
            model.addAttribute("urlError", "La dirección provista no es válida");
        }
        model.addAttribute("links", linkService.findByUser());
        return "links";
    }

}

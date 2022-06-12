package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.entity.Link;
import com.ffmoyano.jird.service.LinkService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String insert(@RequestParam("newUrl") String url, Model model) {
        boolean isUrlValid = linkService.checkIsValid(url);

        Link link = linkService.createLinkFromUrl(url);
        System.out.println(link.toString());
        if(isUrlValid) {
            System.out.println("VALIDOOOO");
            linkService.save(link);
        } else {
            model.addAttribute("urlError", "La dirección provista no es válida");
        }
        model.addAttribute("links", linkService.findByUser());
        return "links";
    }

}

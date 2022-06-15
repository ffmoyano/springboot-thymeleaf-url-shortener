package com.ffmoyano.jird.controller;

import com.ffmoyano.jird.entity.Link;
import com.ffmoyano.jird.service.LinkService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UrlValidator urlValidator;
    private final LinkService linkService;

    public UserController(UrlValidator urlValidator, LinkService linkService) {
        this.urlValidator = urlValidator;
        this.linkService = linkService;
    }

    @GetMapping({"/links"})
    public String links(Model model, HttpServletRequest request) {
        model.addAttribute("links", linkService.findByUser());
        String baseUrl = request.getServerName();
        model.addAttribute("baseUrl", baseUrl.equals("localhost")?baseUrl+":"+request.getServerPort()+"/":baseUrl+"/");
        return "links";
    }

    @PostMapping({"/links"})
    public String insert(@RequestParam("newUrl") String url, Model model, HttpServletRequest request) {
        boolean isUrlValid = linkService.checkIsValid(url);

        Link link = linkService.createLinkFromUrl(url);
        System.out.println("TAMAÑOOOO " + linkService.findByUser());
        if (isUrlValid) {
            linkService.save(link);
        } else {
            model.addAttribute("urlError", "La dirección provista no es válida");
        }
        String baseUrl = request.getServerName();
        model.addAttribute("baseUrl", baseUrl.equals("localhost")?baseUrl+":"+request.getServerPort()+"/":baseUrl+"/");
        model.addAttribute("links", linkService.findByUser());
        return "links";
    }

}

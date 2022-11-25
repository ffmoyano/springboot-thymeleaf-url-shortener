package com.ffmoyano.urlshortener.controller;

import com.ffmoyano.urlshortener.entity.Link;
import com.ffmoyano.urlshortener.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/link")
public class LinkController {


    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }


    @GetMapping({"/"})
    public String links(Model model, HttpServletRequest request) {
        model.addAttribute("links", linkService.findByUser());
        String baseUrl = request.getServerName();
        model.addAttribute("baseUrl", baseUrl.equals("localhost") ? baseUrl + ":" + request.getServerPort() + "/" : baseUrl + "/");
        return "links";
    }

    @PostMapping({"/"})
    public String insert(@RequestParam("newUrl") String url, Model model, HttpServletRequest request) {
        System.out.println("URL:" + url);
        boolean isUrlValid = linkService.checkIsValid(url);
        System.out.println("IS VALID: " + isUrlValid);
        Link link = linkService.createLinkFromUrl(url);

        if (isUrlValid) {
            linkService.save(link);
        } else {
            model.addAttribute("urlError", "La dirección provista no es válida");
        }
        String baseUrl = request.getServerName();
        model.addAttribute("baseUrl", baseUrl.equals("localhost") ? baseUrl + ":" + request.getServerPort() + "/" : baseUrl + "/");
        model.addAttribute("links", linkService.findByUser());
        return "links";
    }

    // el thymeleaf no se traga el th:method="delete"
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request) {


        linkService.deleteById(id);
        String baseUrl = request.getServerName();
        model.addAttribute("baseUrl", baseUrl.equals("localhost") ? baseUrl + ":" + request.getServerPort() + "/" : baseUrl + "/");
        model.addAttribute("links", linkService.findByUser());
        return "links";
    }

}

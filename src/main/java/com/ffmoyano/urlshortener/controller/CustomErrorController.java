package com.ffmoyano.urlshortener.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Throwable throwable) {
        var status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int code = status != null ? Integer.parseInt(status.toString()) : 0;
        if (code == 404) {
            model.addAttribute("error", "No se ha encontrado el recurso");
            return "error";
        } else if (code == 500) {
            model.addAttribute("error", "Ha ocurrido un error al procesar su solicitud");
            return "error";
        } else {
            return "redirect:/link/";
        }
    }
}
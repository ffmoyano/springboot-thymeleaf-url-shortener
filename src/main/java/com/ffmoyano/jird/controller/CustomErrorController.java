package com.ffmoyano.jird.controller;

import org.slf4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private final Logger log;

    public CustomErrorController(Logger log) {
        this.log = log;
    }


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model, Throwable throwable) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int code = status != null ? Integer.parseInt(status.toString()) : 0;
        if (code == 404) {
            model.addAttribute("error", "No se ha encontrado el recurso");
            return "error";
        } else if (code == 500) {
            model.addAttribute("error", "Ha ocurrido un error al procesar su solicitud");
            return "error";
        } else {
            return "redirect:/user/links";
        }
    }

}
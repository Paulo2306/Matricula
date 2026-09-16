package com.ifsp.Matricula.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loginInicial() {
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String home() {
        return "dashboard";
    }
}
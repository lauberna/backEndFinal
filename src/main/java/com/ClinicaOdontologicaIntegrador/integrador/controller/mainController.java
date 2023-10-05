package com.ClinicaOdontologicaIntegrador.integrador.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class mainController {

    @GetMapping("/")
    public String login(){
        return "redirect:/login";
    }

    @GetMapping("admin")
    public String adminRoutes(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/user";
        }
        return "Admin";
    }

    @GetMapping("user")
    public String userRoutes(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }
        return "User";
    }
}

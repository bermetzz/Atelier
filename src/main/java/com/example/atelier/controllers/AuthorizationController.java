package com.example.atelier.controllers;

import com.example.atelier.entities.User;
import com.example.atelier.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "authorization/login";
    }

    @GetMapping("/register")
    public String registrationPage(@ModelAttribute("user") User User) {
        return "authorization/registration";
    }

    @PostMapping("/register")
    public String performRegistration(@ModelAttribute("user") User User) {
        authorizationService.save(User);
        return "redirect:/auth/login";
    }
}

package com.InternetShopIberia.controller;

import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("username", "");
        model.addAttribute("password", "");
        return "login";
    }
}

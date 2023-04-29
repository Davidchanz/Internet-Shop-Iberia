package com.InternetShopIberia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String showStartPage(HttpSession session, Model model) {
        return "startPage";
    }
}

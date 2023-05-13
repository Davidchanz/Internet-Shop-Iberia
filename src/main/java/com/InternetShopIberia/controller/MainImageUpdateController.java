package com.InternetShopIberia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainImageUpdateController {
    @GetMapping("update/image")
    public String updateMainImage(@RequestParam("image") String image, Model model){
        model.addAttribute("chosenImage", image);
        return "productDetail :: #mainImage";
    }
}

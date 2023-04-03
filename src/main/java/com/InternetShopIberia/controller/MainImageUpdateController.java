package com.InternetShopIberia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainImageUpdateController {
    @GetMapping("update/image")
    public String updateMainImage(@RequestParam("image") String image, Model model){
        String path = image.substring(image.lastIndexOf("/")+1);
        model.addAttribute("chosenImage", path);
        return "productDetail :: #mainImage";
    }
}

package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StartPageController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/")
    public String showStartPage(Model model) {
        var categories = categoryService.findRootCategory();
        model.addAttribute("categories", categories);
        return "startPage";
    }
}

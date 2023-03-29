package com.InternetShopIberia.controller;

import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchResultsController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/search")
    private String showSearchResult(@RequestParam("searchRequest") String searchRequest, Model model){
        var products = productService.getAllProductsByName(searchRequest);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findRootCategory());
        return "products";
    }
}

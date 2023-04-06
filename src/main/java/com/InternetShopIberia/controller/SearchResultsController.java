package com.InternetShopIberia.controller;

import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SearchResultsController {
    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    private String showSearchResult(@RequestParam("searchRequest") String searchRequest, HttpSession session, Model model){
        var products = productService.getAllProductsByName(searchRequest);
        model.addAttribute("products", products);
        session.setAttribute("searchRequest", searchRequest);
        return "products";
    }
}

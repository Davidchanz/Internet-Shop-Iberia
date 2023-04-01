package com.InternetShopIberia.controller;

import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productDetail")
    public String showProductDetail(@RequestParam("productId") String productId, Model model){
        var product = productService.getProductById(Long.parseLong(productId));
        model.addAttribute("product", product);
        return "productDetail";
    }
}

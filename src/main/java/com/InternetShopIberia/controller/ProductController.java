package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.exception.ProductNotExistException;
import com.InternetShopIberia.model.Category;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String showProductCategoryPage(@RequestParam("categoryId") String categoryId, Model model){
        var products = productService.getAllProductsInCategoryById(Long.parseLong(categoryId));
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.findRootCategory());
        return "products";
    }
}

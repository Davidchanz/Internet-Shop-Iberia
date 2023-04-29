package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ProductCarouselItem;
import com.InternetShopIberia.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StartPageController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String showStartPage(HttpSession session, Model model) {
        List<ProductCarouselItem> items = new ArrayList<>();
        items.add(createProductCarouselItem("Asus", "a1.jpg"));
        items.add(createProductCarouselItem("Asus", "a2.jpg"));
        items.add(createProductCarouselItem("Asus", "a3.jpg"));
        model.addAttribute("carouselItems", items);
        return "startPage";
    }

    private ProductCarouselItem createProductCarouselItem(String categoryName, String imagePath){
        ProductCarouselItem productCarouselItem = new ProductCarouselItem();
        var category = categoryService.findCategoryTitleLike(categoryName);
        productCarouselItem.setCategoryId(category.get(0).getId());
        productCarouselItem.setImagePath(imagePath);
        return productCarouselItem;
    }
}

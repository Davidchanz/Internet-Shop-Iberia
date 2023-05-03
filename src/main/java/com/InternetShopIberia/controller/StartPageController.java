package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ProductCarouselItem;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
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

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String showStartPage(HttpSession session, Model model) {
        List<ProductCarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(createProductCarouselItem("Asus", "a1.jpg"));
        carouselItems.add(createProductCarouselItem("Asus", "a2.jpg"));
        carouselItems.add(createProductCarouselItem("Asus", "a3.jpg"));
        model.addAttribute("carouselItems", carouselItems);

        List<Product> productList = new ArrayList<>();
        for(int i = 1; i < 6; i++){
            productList.add(productService.getAllProductsInCategoryById(1L).get(i));
        }
        model.addAttribute("productList", productList);
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

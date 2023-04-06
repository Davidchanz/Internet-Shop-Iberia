package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.SearchResult;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchBarController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/s")
    public String getEventCount(@RequestParam("searchInput") String searchInput, ModelMap map) {
        SearchResult searchResult = new SearchResult();
        List<String> searchResults = new ArrayList<>();
        if(searchInput.isEmpty()){
            searchResult.setResults(searchResults);
        }else {
            var products = productService.getAllProductsNameLike(searchInput);
            for (var product : products)
                searchResults.add(product.getName());
            var categories = categoryService.findCategoryTitleLike(searchInput);
            for (var category : categories)
                searchResults.add(category.getTitle());

            searchResult.setResults(searchResults);
        }
        map.addAttribute("searchResults", searchResult);

        return "headerBar :: #dropdown";
    }
}

package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ProductList;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.SearchHistory;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.SearchHistoryService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Controller
public class SearchResultsController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    private String showSearchResult(@RequestParam("searchRequest") String searchRequest, Principal principal, Model model){
        if(searchRequest.isEmpty())
            return "redirect:/";
        else {
            var products = productService.getAllProductsNameLike(searchRequest);
            TreeSet<Product> uniqueProducts = new TreeSet<>(products);
            var categories = categoryService.findCategoryTitleLike(searchRequest);
            for(var category: categories){
                uniqueProducts.addAll(productService.getAllProductsInCategoryById(category.getId()));
            }
            ProductList productList = new ProductList();
            productList.setProducts(uniqueProducts.stream().toList());
            model.addAttribute("products", productList);
            model.addAttribute("searchRequest", searchRequest);

            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setSearchRequest(searchRequest);
            searchHistory.setUser(userService.findUserByUserName(principal.getName()));
            searchHistoryService.addSearchHistory(searchHistory);

            return "redirect:/products?searchRequest=" + searchRequest;
        }
    }
}

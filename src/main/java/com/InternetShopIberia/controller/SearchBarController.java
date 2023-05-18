package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.SearchResult;
import com.InternetShopIberia.dto.SearchResultItem;
import com.InternetShopIberia.model.SearchHistory;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.SearchHistoryService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchBarController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SearchHistoryService searchHistoryService;

    @Autowired
    private UserService userService;

    @GetMapping("/s")
    public String findSearchResults(@RequestParam("searchInput") String searchInput, Principal principal, ModelMap map) {
        User currentUser = userService.findUserByUserName(principal.getName());
        SearchResult searchResult = new SearchResult();
        List<SearchResultItem> searchResults = new ArrayList<>();
        if(searchInput.isEmpty()){
            searchResult.setResults(searchResults);
        }else {
            var searchHistory = searchHistoryService.findAllSearchHistoryLikeRequestByUser(searchInput, currentUser);
            for(var history: searchHistory) {
                searchResults.add(new SearchResultItem(history.getSearchRequest(), true));
                if(searchResults.size() >= 15)
                    break;
            }
            var products = productService.getAllProductsNameLike(searchInput);
            for (var product : products) {
                searchResults.add(new SearchResultItem(product.getName(), false));
                if(searchResults.size() >= 15)
                    break;
            }

            searchResult.setResults(searchResults);
        }
        map.addAttribute("searchResults", searchResult);

        return "headerBar :: #dropdown";
    }
}

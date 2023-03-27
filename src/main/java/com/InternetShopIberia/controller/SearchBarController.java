package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchBarController {
    @GetMapping("/search")
    public String getEventCount(@RequestParam("searchInput") String searchInput, ModelMap map) {
        List<String> searchResults = new ArrayList<>();
        searchResults.add("b");
        searchResults.add("c");
        searchResults.add("d");
        SearchResult searchResult = new SearchResult();
        searchResult.setResults(searchResults);

        map.addAttribute("searchResults", searchResult);

        return "headerBar :: #dropdown";
    }
}

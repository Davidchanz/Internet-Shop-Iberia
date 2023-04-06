package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.Category;
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
    @GetMapping("/")
    public String showStartPage(HttpSession session, Model model) {
        session.removeAttribute("searchRequest");
        return "startPage";
    }
}

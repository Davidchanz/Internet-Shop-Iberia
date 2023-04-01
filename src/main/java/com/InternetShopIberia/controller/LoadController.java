package com.InternetShopIberia.controller;

import com.InternetShopIberia.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoadController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/l")
    public String startSession(ModelMap map){
        map.addAttribute("categories", categoryService.findRootCategory());
        return "sideMenu :: #sidemenu";
    }
}

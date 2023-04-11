package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.Filter;
import com.InternetShopIberia.dto.FilterList;
import com.InternetShopIberia.dto.FilterValue;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CartService;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

import java.util.*;

@Controller
public class LoadController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/l")
    public String loadCategories(ModelMap map){
        map.addAttribute("categories", categoryService.findRootCategory());
        return "sideMenu :: #sidemenu";
    }

    @GetMapping("/c")
    public String loadCartQuantity(Principal principal, ModelMap map){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        map.addAttribute("cartQuantity", cart.getQuantity());
        return "headerBar :: #cart-quantity-form";
    }
}

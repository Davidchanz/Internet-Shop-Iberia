package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

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

    @Autowired
    private UserProductListService userProductListService;

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

    @GetMapping("/u")
    public String loadUserName(Principal principal, ModelMap map){
        User currentUser = userService.findUserByUserName(principal.getName());
        map.addAttribute("userName", currentUser.getUsername());
        return "headerBar :: #userAccount";
    }

    @GetMapping("/user")
    public String loadCollections(Principal principal, ModelMap map){
        User currentUser = userService.findUserByUserName(principal.getName());
        map.addAttribute("collections", currentUser.getCollections());
        return "productDetail :: #add-to-list-select";
    }

    @GetMapping("/coll")
    public String addToCollection(@RequestParam("collectionId") String collectionId, @RequestParam("productId") String productId, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        for(var coll: currentUser.getCollections()){
            if(coll.getId().equals(Long.parseLong(collectionId))){
                var product = productService.getProductById(Long.parseLong(productId));
                var collection = userProductListService.findUserProductListById(Long.parseLong(collectionId));
                collection.getProducts().add(product);
                userProductListService.save(collection);
                model.addAttribute("status", true);
                return "productDetail :: #add-to-list-status";
            }
        }
        model.addAttribute("status", false);
        return "productDetail :: #add-to-list-status";
    }

    @PostMapping("/a")
    public RedirectView addCollection(@RequestParam("collectionName") String collectionName, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        UserProductList collection = new UserProductList();
        collection.setName(collectionName);
        collection.setProducts(new TreeSet<>());
        collection = userProductListService.save(collection);
        currentUser.getCollections().add(collection);
        userService.saveUser(currentUser);
        return new RedirectView("/userAccount", true);
    }
}

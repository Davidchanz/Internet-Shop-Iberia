package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CartService;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    private String ShowUserCart(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        model.addAttribute("products", cart.getProducts());
        model.addAttribute("categories", categoryService.findRootCategory());
        return "products";
    }

    @PostMapping("/cart")
    public String addProductInCart(@RequestParam("productId") String productId, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        Product product = productService.getProductById(Long.parseLong(productId));
        System.out.println(product.getName());
        cart.addProduct(product);
        cartService.updateCart(cart);
        model.addAttribute("products", cart.getProducts());
        model.addAttribute("categories", categoryService.findRootCategory());
        return "products";
    }
}

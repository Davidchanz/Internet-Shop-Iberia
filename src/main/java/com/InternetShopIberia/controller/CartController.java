package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.CartProduct;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CartProductService cartProductService;

    @GetMapping("/cart")
    private String ShowUserCart(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        List<CartProduct> cartProducts = new ArrayList<>();
        for(var product: cart.getProducts()){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(product.getProductId());
            cartProduct.setName(product.getName());
            cartProduct.setPrice(product.getPrice());
            cartProduct.setMainImageSrc(product.getMainImageSrc());
            cartProduct.setQuantity(product.getQuantity());
            cartProducts.add(cartProduct);
        }
        model.addAttribute("products", cartProducts);
        model.addAttribute("categories", categoryService.findRootCategory());
        return "cart";
    }

    @PostMapping("/cart")
    public String addProductInCart(@RequestParam("productId") String productId, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        Product product = productService.getProductById(Long.parseLong(productId));
        boolean cartProductExist = false;
        for(var cartProduct: cart.getProducts()){
            if(Objects.equals(cartProduct.getProductId(), product.getId())) {
                cartProductExist = true;
                System.out.println("exist");
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                cartProductService.updateCartProduct(cartProduct);
                break;
            }
        }
        if(!cartProductExist){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(product.getId());
            cartProduct.setName(product.getName());
            cartProduct.setPrice(product.getPrice());
            cartProduct.setMainImageSrc(product.getMainImageSrc());
            cartProduct.setQuantity(1);
            cartProduct = cartProductService.addCartProduct(cartProduct);
            cart.addProduct(cartProduct);
        }
        cartService.updateCart(cart);
        return "redirect:/cart";
    }

    @GetMapping("q")
    public String updateProductQuantity(@RequestParam("productId") String productId, @RequestParam("quantity") String quantity, Principal principal, ModelMap map){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        Product product = productService.getProductById(Long.parseLong(productId));
        CartProduct cp = null;
        for(var cartProduct: cart.getProducts()){
            if(Objects.equals(cartProduct.getProductId(), product.getId())) {
                cp = cartProduct;
                cartProduct.setQuantity(Integer.parseInt(quantity));
                cartProductService.updateCartProduct(cartProduct);
                break;
            }
        }
        List<CartProduct> cartProducts = new ArrayList<>();
        for(var p: cart.getProducts()){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(p.getProductId());
            cartProduct.setName(p.getName());
            cartProduct.setPrice(p.getPrice());
            cartProduct.setMainImageSrc(p.getMainImageSrc());
            cartProduct.setQuantity(p.getQuantity());
            cartProducts.add(cartProduct);
        }
        map.addAttribute("products", cartProducts);
        return "cart :: #products-list";
    }
}

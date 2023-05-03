package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserPurchase;
import com.InternetShopIberia.model.*;
import com.InternetShopIberia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Scope("session")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartProductService cartProductService;

    @Autowired
    private Currency currency;

    @GetMapping("/cart")
    private String ShowUserCart(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);

        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        for(var product: cart.getProducts()){
            product.setPrice(product.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
        }
        model.addAttribute("products", cart.getProducts());
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
                if(cartProduct.getQuantity() < 999)
                    cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                cartProductService.updateCartProduct(cartProduct);
                break;
            }
        }
        if(!cartProductExist){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(product.getId());
            cartProduct.setName(product.getName());
            cartProduct.setOrigPrice(product.getOrigPrice());
            cartProduct.setMainImage(product.getMainImage());
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
        for(var cartProduct: cart.getProducts()){
            if(Objects.equals(cartProduct.getProductId(), product.getId())) {
                cartProduct.setQuantity(Integer.parseInt(quantity));
                if(cartProduct.getQuantity() > 999)
                    cartProduct.setQuantity(999);
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
            cartProduct.setMainImage(p.getMainImage());
            cartProduct.setQuantity(p.getQuantity());
            cartProducts.add(cartProduct);
        }
        map.addAttribute("products", cartProducts);
        return "cart :: #products-list";
    }

    @GetMapping("/cart/delete")
    public String deleteProductFromCart(@RequestParam("productId") String productId, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);
        Product product = productService.getProductById(Long.parseLong(productId));
        for(var cartProduct: cart.getProducts()){
            if(cartProduct.getProductId().equals(product.getId())){
                cart.getProducts().remove(cartProduct);
                cartProductService.removeCartProduct(cartProduct);
                break;
            }
        }
        cartService.updateCart(cart);
        return "redirect:/cart";
    }

    @GetMapping("/buy")
    public String buyAllCartProduct(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        UserPurchase purchase = new UserPurchase();
        purchase.setEmail(currentUser.getEmail());
        purchase.setFullName(currentUser.getFirstName() + " " + currentUser.getLastName());
        model.addAttribute("purchase", purchase);
        return "buyProducts";
    }
}

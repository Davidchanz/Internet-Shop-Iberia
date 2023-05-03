package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.CollectionDTO;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.Currency;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
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

    @Autowired
    private Currency currency;

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
    public String loadUserName(Principal principal, ModelMap map) {
        User currentUser = userService.findUserByUserName(principal.getName());
        map.addAttribute("userName", currentUser.getUsername());
        return "headerBar :: #userAccount";
    }

    @GetMapping("/coll")
    public String manageCollection(@RequestParam("collectionId") String collectionId, @RequestParam("productId") String productId, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        List<CollectionDTO> collectionDTOList = new ArrayList<>();
        var product = productService.getProductById(Long.parseLong(productId));
        for(var coll: currentUser.getCollections()){
            if(coll.getId().equals(Long.parseLong(collectionId))){
                if(coll.getProducts().contains(product)){
                    coll.getProducts().remove(product);
                }else {
                    coll.getProducts().add(product);
                }
                userProductListService.save(coll);
            }
            CollectionDTO collectionDTO = new CollectionDTO();
            collectionDTO.setCollection(coll);
            if(coll.getProducts().contains(product)){
                collectionDTO.setProductExist(true);
            }else {
                collectionDTO.setProductExist(false);
            }
            collectionDTOList.add(collectionDTO);
        }
        model.addAttribute("collectionDTOList", collectionDTOList);
        return "productDetail :: #add-to-list-select";
    }

    @PostMapping("/a")
    public RedirectView addCollection(@RequestParam("newName") String collectionName, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        UserProductList collection = new UserProductList();
        collection.setName(collectionName);
        collection.setProducts(new TreeSet<>());
        collection = userProductListService.save(collection);
        currentUser.getCollections().add(collection);
        userService.saveUser(currentUser);
        return new RedirectView("/userAccount", true);
    }

    @GetMapping("/coll/d")
    public RedirectView deleteCollection(@RequestParam("name") String name, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        for(var collection: currentUser.getCollections()){
            if(collection.getName().equals(name)){
                currentUser.getCollections().remove(collection);
                userProductListService.deleteCollection(userProductListService.findUserProductListById(collection.getId()));
                return new RedirectView("/userAccount", true);
            }
        }
        return new RedirectView("/userAccount", true);
    }

    @PostMapping("/coll/n")
    public RedirectView changeCollectionName(@RequestParam("collectionName") String collectionName, @RequestParam("newCollectionName") String newCollectionName, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        for(var collection: currentUser.getCollections()){
            if(collection.getName().equals(collectionName)){
                collection.setName(newCollectionName);
                userProductListService.save(collection);
                return new RedirectView("/userAccount", true);
            }
        }
        return new RedirectView("/userAccount", true);
    }

    @GetMapping("/p")
    public String loadCartPrice(Principal principal, ModelMap map) {
        User currentUser = userService.findUserByUserName(principal.getName());
        var cart = cartService.findCartByUser(currentUser);
        BigDecimal cartPrice = new BigDecimal(0L);

        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        for (var cartProduct: cart.getProducts()){
            cartProduct.setPrice(cartProduct.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
            cartPrice = cartPrice.add(cartProduct.getPrice().multiply(new BigDecimal(cartProduct.getQuantity())));
        }
        map.addAttribute("cartPrice", cartPrice);
        return "cart :: #price";
    }
}

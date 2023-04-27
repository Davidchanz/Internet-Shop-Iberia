package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.CollectionDTO;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CategoryService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/productDetail")
    public String showProductDetail(@RequestParam("productId") String productId, Principal principal, Model model){
        var product = productService.getProductById(Long.parseLong(productId));
        model.addAttribute("product", product);

        User currentUser = userService.findUserByUserName(principal.getName());
        List<CollectionDTO> collectionDTOList = new ArrayList<>();
        for(var collection: currentUser.getCollections()){
            CollectionDTO collectionDTO = new CollectionDTO();
            collectionDTO.setCollection(collection);
            if(collection.getProducts().contains(product)){
                collectionDTO.setProductExist(true);
            }else {
                collectionDTO.setProductExist(false);
            }
            collectionDTOList.add(collectionDTO);
        }
        model.addAttribute("collectionDTOList", collectionDTOList);
        return "productDetail";
    }
}

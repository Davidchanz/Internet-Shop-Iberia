package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.User;
import com.InternetShopIberia.model.UserProductList;
import com.InternetShopIberia.service.UserProductListService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class UserAccount {
    @Autowired
    private UserService userService;

    @Autowired
    private UserProductListService userProductListService;

    @GetMapping("/userAccount")
    private String showUserAccountPage(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        model.addAttribute("user", currentUser);
        return "userAccountInfo";
    }

    @GetMapping("/userCollection")
    public RedirectView showUserCollection(@RequestParam(name = "id") String id, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        for(var collection: currentUser.getCollections()){
            if(collection.getId().equals(Long.parseLong(id))){
                return new RedirectView("/products?collectionId="+id, true);
            }
        }
        return new RedirectView("/error", true);//TODO
    }
}

package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserAccount {
    @Autowired
    private UserService userService;

    @GetMapping("/userAccount")
    private String showUserAccountPage(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        model.addAttribute("user", currentUser);
        return "userAccountInfo";
    }
}

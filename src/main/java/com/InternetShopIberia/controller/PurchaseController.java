package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.dto.UserPurchase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class PurchaseController {

    @PostMapping("/purchase")
    public String confirmPurchase(@ModelAttribute("purchase") UserPurchase purchase, Principal principal, Model model){
//TODO
        return "purchaseSuccess";
    }
}

package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.exception.UserAlreadyExistException;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CartService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/registration")
    public String showRegistrationPage(Model model){
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, HttpServletRequest request, Errors errors) {
        ModelAndView mav = new ModelAndView("registration", "user", userDto);
        try {
            User registered = userService.registerNewUserAccount(userDto);
            Cart cart = new Cart();
            cart.setUser(registered);
            cart.setProducts(new ArrayList<>());
            cartService.addCart(cart);
        } catch (UserAlreadyExistException uaeEx) {
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("successRegister", "user", userDto);
    }
}

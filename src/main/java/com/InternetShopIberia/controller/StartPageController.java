package com.InternetShopIberia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String showStartPage(HttpSession session, Model model) {

        //Error handle and show
        /*try{}
        catch(RuntimeException e){
            Error error = new Error();
            error.setCode(102L);
            error.setMessage("Test error!");
            model.addAttribute("error", error);
            return "startPage";
        }*/

        return "startPage";
    }
}

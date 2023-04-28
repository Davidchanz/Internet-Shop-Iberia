package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.ErrorDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String showErrorPage(@ModelAttribute("userError") ErrorDto error, Model model){
        System.out.println("wertg");
        System.out.println(error.getMessage());
        model.addAttribute("userError", error);
        return "error";
    }

    /*@GetMapping("/errorTest")
    public RedirectView test(RedirectAttributes redirectAttrs, Model model){
        ErrorDto error = new ErrorDto();
        error.setMessage("qwergfds");
        error.setCode(123L);
        redirectAttrs.addFlashAttribute("userError", error);
        return new RedirectView("/error", true);
    }*/
}

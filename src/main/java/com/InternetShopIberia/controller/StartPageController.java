package com.InternetShopIberia.controller;

import com.InternetShopIberia.model.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StartPageController {
    @GetMapping("/")
    public String contacts(Model model) {
        Category categories = new Category("Categories", List.of(
                new Category("Iphone", List.of(
                        new Category("5", List.of()),
                        new Category("5S", List.of()),
                        new Category("6", List.of()),
                        new Category("6S", List.of())
                )),
                new Category("Android", List.of(
                        new Category("Galaxy S5", List.of()),
                        new Category("Galaxy S6", List.of())
                )),
                new Category("Laptop", List.of(
                        new Category("Asus", List.of(
                                new Category("VivoBook 15S", List.of())
                        )),
                        new Category("Acer", List.of())
                ))
        )
        );


        model.addAttribute("node", categories);
        return "Test";
    }
}

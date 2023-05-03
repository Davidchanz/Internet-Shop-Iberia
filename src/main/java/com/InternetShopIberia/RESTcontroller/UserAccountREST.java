package com.InternetShopIberia.RESTcontroller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ResourceBundle;

@RestController
public class UserAccountREST {

    @GetMapping("/userCollection/dell")
    public String showCollectionDeleteWarning(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", LocaleContextHolder.getLocale());
        return resourceBundle.getString("warning.sure") + "," + resourceBundle.getString("warning.collection.delete");
    }
}

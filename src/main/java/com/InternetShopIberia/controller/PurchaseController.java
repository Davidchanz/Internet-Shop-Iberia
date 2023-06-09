package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.dto.UserPurchase;
import com.InternetShopIberia.mail.EmailService;
import com.InternetShopIberia.model.*;
import com.InternetShopIberia.service.CartService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserService;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.File;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class PurchaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private Currency currency;

    @PostMapping("/purchase")
    public String confirmPurchase(@ModelAttribute("purchase") UserPurchase purchase, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);

        List<CartProduct> productList = new ArrayList<>(cart.getProducts());

        BigDecimal price = new BigDecimal(0L);

        var cur = java.util.Currency.getInstance(LocaleContextHolder.getLocale()).getCurrencyCode();
        for(var product: cart.getProducts()){
            product.setPrice(product.getOrigPrice().multiply(BigDecimal.valueOf(currency.getRate(cur).getCurRate())));
            price = price.add(product.getPrice().multiply(new BigDecimal(product.getQuantity())));
        }

        Context ctx = new Context(LocaleContextHolder.getLocale());

        ctx.setVariable("purchase", purchase);

        ctx.setVariable("productList", productList);
        ctx.setVariable("price", price.longValue());

        final String result = templateEngine.process("emailPurchase", ctx);

        emailService.sendMimeMessage(purchase.getEmail(), "Order Detail", result);

        model.addAttribute("email", currentUser.getEmail());
        return "purchaseSuccess";
    }
}

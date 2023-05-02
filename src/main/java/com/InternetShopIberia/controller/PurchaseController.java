package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.dto.UserPurchase;
import com.InternetShopIberia.mail.EmailService;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.CartProduct;
import com.InternetShopIberia.model.Product;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.CartService;
import com.InternetShopIberia.service.ProductService;
import com.InternetShopIberia.service.UserService;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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

    @PostMapping("/purchase")
    public String confirmPurchase(@ModelAttribute("purchase") UserPurchase purchase, Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        Cart cart = cartService.findCartByUser(currentUser);

        List<CartProduct> productList = new ArrayList<>(cart.getProducts());

        BigDecimal price = new BigDecimal(0L);
        for(var product: cart.getProducts()){
            price = price.add(product.getPrice().multiply(new BigDecimal(product.getQuantity())));
        }

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        templateResolver.setForceTemplateMode(true);

        templateEngine.setTemplateResolver(templateResolver);

        Context ctx = new Context();

        ctx.setVariable("header", "Order Detail");
        ctx.setVariable("subject", "Hi, this is your order in Internet-Shop 'Iberia'!");
        ctx.setVariable("purchase", purchase);

        ctx.setVariable("productList", productList);
        ctx.setVariable("price", price.longValue());

        List<FileSystemResource> attachments = new ArrayList<>();
        for(var product: productList){
            var file = new FileSystemResource("/home/katsitovlis/Documents/Project/Spring/Internet-Shop-Iberia/src/main/resources/static/images/"+product.getMainImage().getPath());//TODO image server
            ctx.setVariable(product.getMainImage().getPath(), file);
            attachments.add(file);
        }

        final String result = templateEngine.process("emailPurchase", ctx);

        emailService.sendMimeMessage(purchase.getEmail(), "Order Detail", result, attachments.toArray(new FileSystemResource[0]));

        model.addAttribute("email", currentUser.getEmail());
        return "purchaseSuccess";
    }
}

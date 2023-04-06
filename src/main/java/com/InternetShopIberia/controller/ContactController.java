package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.SupportRequestDto;
import com.InternetShopIberia.dto.UserDto;
import com.InternetShopIberia.exception.UserAlreadyExistException;
import com.InternetShopIberia.mail.EmailService;
import com.InternetShopIberia.mail.GMailServiceImpl;
import com.InternetShopIberia.model.Cart;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.SupportRequestService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@Controller
public class ContactController {
    @Autowired
    private SupportRequestService supportRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/contact")
    public String showContactPage(Model model){
        SupportRequestDto supportRequest = new SupportRequestDto();
        model.addAttribute("supportRequest", supportRequest);
        return "contact";
    }

    @PostMapping("/contact")
    public ModelAndView registerSupportRequest(@ModelAttribute("supportRequest") SupportRequestDto supportRequestDto, Principal principal, HttpServletRequest request, Errors errors) {
        ModelAndView mav = new ModelAndView();
        User currentUser = userService.findUserByUserName(principal.getName());
        var supportRequest = supportRequestService.registerRequest(supportRequestDto, currentUser);

        String subject = "Contacting Support";
        String text = "Your support ticket has been registered!\n\n";

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        templateResolver.setForceTemplateMode(true);

        templateEngine.setTemplateResolver(templateResolver);

        Context ctx = new Context();

        ctx.setVariable("header", text);
        ctx.setVariable("code", supportRequest.getCode());
        ctx.setVariable("subject", supportRequest.getSubject());
        ctx.setVariable("message", supportRequest.getMessage());
        final String result = templateEngine.process("mail", ctx);

        emailService.sendSimpleMessage(supportRequest.getEmail(), subject, result);
        return new ModelAndView("supportSuccess");
    }
}

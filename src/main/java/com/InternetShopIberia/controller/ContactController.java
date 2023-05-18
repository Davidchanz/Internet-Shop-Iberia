package com.InternetShopIberia.controller;

import com.InternetShopIberia.dto.SupportRequestDto;
import com.InternetShopIberia.mail.EmailService;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.service.SupportRequestService;
import com.InternetShopIberia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.security.Principal;

@Controller
public class ContactController {
    @Autowired
    private SupportRequestService supportRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("/contact")
    public String showContactPage(Principal principal, Model model){
        User currentUser = userService.findUserByUserName(principal.getName());
        SupportRequestDto supportRequest = new SupportRequestDto();
        supportRequest.setEmail(currentUser.getEmail());
        model.addAttribute("supportRequest", supportRequest);
        return "contact";
    }

    @PostMapping("/contact")
    public String registerSupportRequest(@ModelAttribute("supportRequest") SupportRequestDto supportRequestDto, Principal principal, Model model) {
        User currentUser = userService.findUserByUserName(principal.getName());
        var supportRequest = supportRequestService.registerRequest(supportRequestDto, currentUser);

        Context ctx = new Context(LocaleContextHolder.getLocale());

        ctx.setVariable("code", supportRequest.getCode());
        ctx.setVariable("message", supportRequest.getMessage());

        final String result = templateEngine.process("mail", ctx);

        emailService.sendMimeMessage(supportRequest.getEmail(), "Contacting Support", result);

        model.addAttribute("email", currentUser.getEmail());
        return "supportSuccess";
    }
}

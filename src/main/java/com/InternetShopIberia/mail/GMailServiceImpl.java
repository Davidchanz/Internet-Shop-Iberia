package com.InternetShopIberia.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class GMailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            //mimeMessage.setContent(htmlMsg, "text/html");
            helper.setText(text, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("internetshopiberiasupp@gmail.com");
            emailSender.send(mimeMessage);
        }catch (MailException | MessagingException ex ){
            System.err.println(ex.getMessage());
        }


        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("internetshopiberiasupp@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        }catch (MailException ex){
            System.err.println(ex.getMessage());
        }*/

    }

    @Override
    public void sendMimeMessage(String to, String subject, String text, FileSystemResource[] attachments) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            //mimeMessage.setContent(htmlMsg, "text/html");
            helper.setText(text, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("internetshopiberiasupp@gmail.com");
            for (var attach: attachments)
                helper.addAttachment(attach.getFilename(), attach);
            emailSender.send(mimeMessage);
        }catch (MailException | MessagingException ex ){
            System.err.println(ex.getMessage());
        }
    }
}
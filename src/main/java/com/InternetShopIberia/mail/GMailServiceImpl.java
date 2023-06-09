package com.InternetShopIberia.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

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
    }

    @Override
    public void sendMimeMessage(String to, String subject, String text) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        String attachmentsPath = "src/main/resources/static/images/logo.png";

        FileSystemResource logo = new FileSystemResource(attachmentsPath);
        try {
            helper.setText(text, true);
            helper.addInline("logo", logo);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("internetshopiberiasupp@gmail.com");
            emailSender.send(mimeMessage);
        }catch (MailException | MessagingException ex ){
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void sendAttachmentMessage(String to, String subject, String text, List<FileSystemResource> attachments) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        }
        String attachmentsPath = "src/main/resources/static/images/logo.png";

        FileSystemResource logo = new FileSystemResource(attachmentsPath);
        try {
            helper.setText(text, true);
            helper.addInline("logo", logo);
            for (var attach: attachments) {
                helper.addInline(attach.getFilename(), attach);
            }
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("internetshopiberiasupp@gmail.com");
            emailSender.send(mimeMessage);
        }catch (MailException | MessagingException ex ){
            System.err.println(ex.getMessage());
        }
    }
}
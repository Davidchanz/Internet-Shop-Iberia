package com.InternetShopIberia.mail;

import org.springframework.core.io.FileSystemResource;

import javax.mail.internet.MimeMessage;
import java.io.File;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendMimeMessage(String to, String subject, String text, FileSystemResource[] attachments);
}

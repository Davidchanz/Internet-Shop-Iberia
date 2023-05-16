package com.InternetShopIberia.mail;

import org.springframework.core.io.FileSystemResource;

import java.util.List;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendMimeMessage(String to, String subject, String text);

    void sendAttachmentMessage(String to, String subject, String text, List<FileSystemResource> attachments);
}

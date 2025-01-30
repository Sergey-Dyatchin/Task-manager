package ru.dyatchin.Task_manager.service.email.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.dyatchin.Task_manager.model.Mail;
import ru.dyatchin.Task_manager.service.email.EmailService;

/**
 * Сервис отправки email
 */
@Service
public class EmailServiceImpl implements EmailService {
    /**
     * Для отправки почты по протоколу SMTP
     */
    private JavaMailSender mailSender;

    /**
     * Конструктор
     * @param mailSender
     */
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendEmail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        mailSender.send(message);
    }
}

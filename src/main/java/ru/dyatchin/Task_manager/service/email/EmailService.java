package ru.dyatchin.Task_manager.service.email;

import ru.dyatchin.Task_manager.model.Mail;

/**
 * Функциональный интерфейс отправки email
 */
public interface EmailService {
   void sendEmail(Mail mail);
}

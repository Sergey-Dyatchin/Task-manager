package ru.dyatchin.Task_manager.service.mediator.impl;


import org.springframework.stereotype.Service;
import ru.dyatchin.Task_manager.model.Mail;
import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.model.User;
import ru.dyatchin.Task_manager.service.email.EmailService;
import ru.dyatchin.Task_manager.service.mediator.Mediator;

/**
 * Сервис получения уведомлений и информирования пользователей
 * реализация паттерна Mediator
 */

@Service
public class MessageMediator implements Mediator {

    /**
     * Сервис отправки email
     */
    private EmailService emailService;

    /**
     * Конструктор
     *
     * @param emailService
     */
    public MessageMediator(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void notifyCustom(Object sender, String message) {
        Mail mail = new Mail();
        if (sender.getClass() == User.class) {
            mail.setTo(getToInUser(sender));
            mail.setSubject(message);
            mail.setBody(createNewUserNotify(sender, message));
        } else if ((sender.getClass() == Task.class)) {
            mail.setTo(getToInTask(sender));
            mail.setSubject(message);
            mail.setBody(taskNotify(sender, message));
        }
        sendNotify(mail);
    }

    /**
     * Получаем адресата
     *
     * @param sender
     * @return
     */
    private String[] getToInTask(Object sender) {
        Task task = (Task) sender;
        return new String[]{task.getAuthor().getEmail(), task.getExecutor().getEmail()};
    }

    /**
     * Получаем адресата
     *
     * @param sender
     * @return
     */
    private String[] getToInUser(Object sender) {
        User user = (User) sender;
        return new String[]{user.getEmail()};
    }

    /**
     * Создает текст сообщения о задаче
     *
     * @param sender
     * @param message
     * @return
     */
    private String taskNotify(Object sender, String message) {
        Task task = (Task) sender;
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.append(":\n").append(task.getId()).append("\n")
                .append(task.getTitle()).append("\n")
                .append(task.getContent()).append("\n")
                .append(task.getComment()).append("\n")
                .append(task.getCreatedDate()).append("\n")
                .append(task.getDeadlineDate()).append("\n")
                .append(task.getAuthor()).append("\n")
                .append(task.getExecutor()).append("\n")
                .append(task.getStatus()).append("\n");
        return stringBuilder.toString();
    }

    /**
     * Создает текст сообщения о новом пользователе
     *
     * @param sender
     * @param message
     * @return
     */
    private String createNewUserNotify(Object sender, String message) {
        User user = (User) sender;
        return message + ":\n" +
                "Ваше имя: " + user.getName() + "\n" +
                "Ваш email: " + user.getEmail();
    }

    /**
     * Отправляем email
     *
     * @param mail
     */
    private void sendNotify(Mail mail) {
        emailService.sendEmail(mail);
    }


}

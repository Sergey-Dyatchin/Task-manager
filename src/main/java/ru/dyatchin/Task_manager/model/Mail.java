package ru.dyatchin.Task_manager.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Сущность электронное письмо
 */
@Getter
@Setter
public class Mail {
    /**
     * Адресаты
     */
    private String[] to;
    /**
     * Тема письма
     */
    private String subject;
    /**
     * Тело письма
     */
    private String body;

}

package ru.dyatchin.Task_manager.service.mediator;

/**
 * Функциональный интерфейс паттерна Mediator
 */
public interface Mediator {
    void notifyCustom(Object sender, String message);
}

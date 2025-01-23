package ru.dyatchin.Task_manager.model;

/**
 * Перечисление возможных статусов задач
 */
public enum Status {
    CREATED("Создана"),
    ASSIGNED("Назначена"),
    TO_WORK("В работе"),
    COMPLETED("Завершена"),
    CANCELLED("Отменена");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}

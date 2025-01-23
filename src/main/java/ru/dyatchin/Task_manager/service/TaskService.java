package ru.dyatchin.Task_manager.service;

import ru.dyatchin.Task_manager.model.Task;

import java.util.List;

/**
 * Сервис Task
 */
public interface TaskService {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Task task);

    void deleteTask(Long id);

    List<Task> getNineSortByDeadlineDate();
}

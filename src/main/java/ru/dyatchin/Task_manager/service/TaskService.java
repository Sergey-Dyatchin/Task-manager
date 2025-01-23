package ru.dyatchin.Task_manager.service;

import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.model.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task updateTask(Task task);

    void deleteTask(Long id);

    List<Task> listTasks();

    List<Task> getTasksByUser(User user);

    public List<Task> getTenSortByDeadlineDate();
}

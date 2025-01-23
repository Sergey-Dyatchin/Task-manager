package ru.dyatchin.Task_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.dyatchin.Task_manager.model.Status;
import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.model.User;
import ru.dyatchin.Task_manager.repository.TaskRepository;
import ru.dyatchin.Task_manager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        task.setCreatedDate(LocalDate.now());
        if (task.getExecutor() == null) {
            task.setStatus(Status.CREATED);
        } else {
            task.setStatus(Status.ASSIGNED);
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(null);
    }

    @Override
    public Task updateTask(Task task) {
        Task taskByID = getTaskById(task.getId());

        taskByID.setTitle(task.getTitle());
        taskByID.setContent(task.getContent());
        taskByID.setComment(task.getComment());
        taskByID.setStatus(task.getStatus());
        taskByID.setDeadlineDate(task.getDeadlineDate());
        taskByID.setExecutor(task.getExecutor());

        return taskRepository.save(taskByID);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }


    @Override
    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    @Override
    public List<Task> getTenSortByDeadlineDate() {
        Page<Task> resultsPage = taskRepository.findAll(PageRequest
                .of(0, 9, Sort.by("deadlineDate")));
        return resultsPage.toList();
    }

}

package ru.dyatchin.Task_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.dyatchin.Task_manager.model.Status;
import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.repository.TaskRepository;
import ru.dyatchin.Task_manager.service.TaskService;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис задач
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    /**
     * Репозиторий Task для работы с БД.
     */
    private final TaskRepository taskRepository;

    /**
     * Получаем задачу
     * устанавливаем дату создания
     * задаем статус по условию
     * сохраняем
     *
     * @param task задача
     * @return созданную задачу
     */
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

    /**
     * Получаем список задач из репозитория
     *
     * @return список задач List<Task>
     */
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Получаем задачу по id или исключение если задачи с таки id нет
     *
     * @param id
     * @return Task задача
     */
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(null);
    }

    /**
     * Обновление задачи кроме полей id, createDate и author
     *
     * @param task задача
     * @return Task
     */
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

    /**
     * Удаление задачи по id
     *
     * @param id задачи
     */
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Получение списка из 9 задач с самым ранним дедлайном
     * сортированных по дате дедлайна
     *
     * @return List<Task> не более 9
     */
    @Override
    public List<Task> getNineSortByDeadlineDate() {
        Page<Task> resultsPage = taskRepository.findAll(PageRequest
                .of(0, 9, Sort.by("deadlineDate")));
        return resultsPage.toList();
    }

}

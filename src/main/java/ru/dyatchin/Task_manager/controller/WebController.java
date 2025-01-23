package ru.dyatchin.Task_manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dyatchin.Task_manager.model.Status;
import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.model.User;
import ru.dyatchin.Task_manager.service.TaskService;
import ru.dyatchin.Task_manager.service.UserService;

import java.util.List;

/**
 * Контроллер обработки запросов
 */
@AllArgsConstructor
@Controller
public class WebController {

    /**
     * Сервис задач.
     */
    private final TaskService taskService;

    /**
     * Сервис пользователей.
     */
    private UserService userService;

    /**
     * Загружаем главную страницу
     *
     * @param model веб страницы
     *              в модель передается список задач из не более 9 с самым ранним дедлайном
     * @return home.html
     */
    @GetMapping("/home")
    String home(Model model) {
        List<Task> tasks = taskService.getNineSortByDeadlineDate();
        model.addAttribute("tasks", tasks);
        return "home";
    }

    /**
     * Загружаем форму создания новой задачи
     *
     * @param model веб страницы
     *              В модель передается список пользователей User возможных исполнителей
     * @param task  Задача
     * @return newTask.html
     */
    @GetMapping("/newTask")
    public String createDiaryForm(Model model, Task task) {
        List<User> executors = userService.getAllUsers();
        model.addAttribute("executors", executors);
        return "/newTask";
    }

    /**
     * Получаем новую задачу
     * устанавливаем автора текущего пользователя
     * сохраняем новую задачу
     * отображаем список всех существующих задач
     *
     * @param model модель веб страницы
     *              в модель передается список всех задач
     * @param task  задача
     * @return currentTasks.html
     */
    @PostMapping("/currentTasks")
    public String createTask(Model model, Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        task.setAuthor(user);
        taskService.createTask(task);
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks", taskList);
        return "currentTasks";
    }

    /**
     * Отображаем список всех существующих задач
     *
     * @param model модель веб страницы
     *              в модель передается список всех задач
     * @return currentTasks.html
     */
    @GetMapping("/currentTasks")
    public String viewTask(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks", taskList);
        return "currentTasks";
    }

    /**
     * Удаляем задачу с id если ее создал текущий пользователь
     * перенаправление и отображение текущих задач
     *
     * @param id id задачи
     * @return redirect:/currentTasks
     */
    @GetMapping("taskDelete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        Task task = taskService.getTaskById(id);
        if (user.equals(task.getAuthor())) {
            taskService.deleteTask(id);
        }
        return "redirect:/currentTasks";
    }

    /**
     * Загружаем форму для редактирования задачи по id
     *
     * @param id    id задачи
     * @param model модель веб страницы
     *              в модель передается task задача
     *              в модель передается список User возможных исполнителей
     *              в модель передается список Status возможных статусов
     *              в модель передается isOwner true/false является ли пользователь автором задачи (ограничение прав на редактирование полей)
     * @return currentTasksUpdate.html
     */
    @GetMapping("/currentTasksUpdate/{id}")
    public String updateTask(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        List<User> executors = userService.getAllUsers();
        model.addAttribute("executors", executors);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("isOwner", user.equals(task.getAuthor()));
        return "currentTasksUpdate";
    }

    /**
     * Сохраняем отредактированную задачу
     * перенаправление на список задач
     *
     * @param task задача
     * @return redirect:/currentTasks
     */
    @PostMapping("/currentTasksUpdate")
    public String updateTask(Task task) {
        taskService.getTaskById(task.getId());
        taskService.updateTask(task);
        return "redirect:/currentTasks";
    }

}

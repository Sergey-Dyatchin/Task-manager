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

@AllArgsConstructor
@Controller
public class WebController {

    private final TaskService taskService;

    private UserService userService;

    @GetMapping("/home")
    String home() {
        return "home";
    }

    @GetMapping("/newTask")
    public String createDiaryForm(Model model ,Task task) {
        List<User> executors = userService.getAllUsers();
        model.addAttribute("executors", executors);
        return "/newTask";
    }

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

    @GetMapping("/currentTasks")
    public String viewTask(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        model.addAttribute("tasks", taskList);
        return "currentTasks";
    }

    @GetMapping("taskDelete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        //Task task = taskService.getTaskById(id);
       // User user = task.getAuthor();
        //userService.saveUser(user);
        taskService.deleteTask(id);
        return "redirect:/currentTasks";
    }

    @GetMapping("/currentTasksUpdate/{id}")
    public String updateTask(@PathVariable("id") Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        List<User> executors = userService.getAllUsers();
        model.addAttribute("executors", executors);
                model.addAttribute("statuses", Status.values());
        return "currentTasksUpdate";
    }

    @PostMapping("/currentTasksUpdate")
    public String updateTask(Task task) {
        taskService.getTaskById(task.getId());
        taskService.updateTask(task);
        return "redirect:/currentTasks";
    }

}

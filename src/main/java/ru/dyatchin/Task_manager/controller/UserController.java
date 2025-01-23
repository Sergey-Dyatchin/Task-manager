package ru.dyatchin.Task_manager.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.dyatchin.Task_manager.dto.UserDto;
import ru.dyatchin.Task_manager.model.User;
import ru.dyatchin.Task_manager.service.UserService;

import java.util.List;

/**
 * Контроллер пользователей.
 */

@Controller
public class UserController {

    /**
     * Сервис пользователей.
     */
    private UserService userService;

    /**
     * Конструктор.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Перенаправление на домашнюю страницу после успешного входа
     *
     * @return redirect:/home
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    /**
     * Загружаем форму для входа
     *
     * @return login.html
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Загружаем форму для регистрации
     *
     * @param model форма UserDto
     * @return register.html
     */
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Сохраняем нового пользователя
     *
     * @param user   UserDto из формы регистрации
     * @param result результат валидации
     * @param model  модель для веб страницы
     * @return redirect:/register?success
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null,
                    "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUserDto(user);
        return "redirect:/register?success";
    }

    /**
     * Показать страницу с текущими пользователями
     *
     * @param model модель для веб страницы
     * @return users.html
     */
    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}

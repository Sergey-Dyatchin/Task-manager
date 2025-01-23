package ru.dyatchin.Task_manager.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dyatchin.Task_manager.dto.UserDto;
import ru.dyatchin.Task_manager.model.Role;
import ru.dyatchin.Task_manager.model.User;
import ru.dyatchin.Task_manager.repository.RoleRepository;
import ru.dyatchin.Task_manager.repository.UserRepository;
import ru.dyatchin.Task_manager.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис пользователей
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Репозиторий User
     */
    private UserRepository userRepository;

    /**
     * Репозиторий Role
     */
    private RoleRepository roleRepository;

    /**
     * PasswordEncoder для безопасного хранения пароля
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Конструктор
     *
     * @param userRepository
     * @param roleRepository
     * @param passwordEncoder
     */
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Сохранение User из UserDto
     * поле name парсится из полей UserDto firstName и lastName крайние пробелы удаляются
     * всем пользователям задается роль ROLE_ADMIN (упрощение на текущем этапе проекта)
     *
     * @param userDto
     */
    @Override
    public void saveUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName().trim() + " " + userDto.getLastName().trim());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    /**
     * Найти пользователя по email
     *
     * @param email
     * @return
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Получение всех User и приведение их к типу UserDto
     *
     * @return List<UserDto>
     */
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    /**
     * Получение всех User
     *
     * @return List<User>
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Приведение User в UserDto
     *
     * @param user
     * @return
     */
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    /**
     * созданаие роли ROLE_ADMIN
     *
     * @return
     */
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    /**
     * Сохранение User
     *
     * @param user
     */
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }


}

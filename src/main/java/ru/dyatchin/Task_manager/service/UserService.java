package ru.dyatchin.Task_manager.service;



import ru.dyatchin.Task_manager.dto.UserDto;
import ru.dyatchin.Task_manager.model.User;

import java.util.List;

public interface UserService {
    void saveUserDto(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();

    List<User> getAllUsers();

    void saveUser(User user);
}

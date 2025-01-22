package ru.dyatchin.Task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dyatchin.Task_manager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

package ru.dyatchin.Task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dyatchin.Task_manager.model.User;

/**
 * Репозиторий User для работы с БД.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

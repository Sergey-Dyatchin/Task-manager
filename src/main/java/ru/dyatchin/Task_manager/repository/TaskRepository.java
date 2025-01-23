package ru.dyatchin.Task_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dyatchin.Task_manager.model.Task;

import java.util.Optional;

/**
 * Репозиторий Task для работы с БД.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

}

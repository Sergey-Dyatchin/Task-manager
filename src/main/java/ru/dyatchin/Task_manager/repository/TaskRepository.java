package ru.dyatchin.Task_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dyatchin.Task_manager.model.Task;
import ru.dyatchin.Task_manager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findById(Long id);

    List<Task> findByUser(User user);

}

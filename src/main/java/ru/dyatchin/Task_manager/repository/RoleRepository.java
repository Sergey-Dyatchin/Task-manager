package ru.dyatchin.Task_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dyatchin.Task_manager.model.Role;

/**
 * Репозиторий Role для работы с БД.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

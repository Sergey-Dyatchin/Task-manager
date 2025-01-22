package ru.dyatchin.Task_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.dyatchin.Task_manager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

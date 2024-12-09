package ru.kata.springsecurityweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.springsecurityweb.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
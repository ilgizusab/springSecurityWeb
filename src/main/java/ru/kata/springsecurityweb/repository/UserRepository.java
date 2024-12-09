package ru.kata.springsecurityweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.springsecurityweb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
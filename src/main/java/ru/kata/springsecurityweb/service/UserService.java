package ru.kata.springsecurityweb.service;

import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.model.Role;
import ru.kata.springsecurityweb.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    UserDTO getUserDTOByUsername(String username);

    void saveUser(UserDTO userDTO);

    void deleteUser(Long id);

    User findByUsername(String username);

    List<Role> getAllRoles();
}

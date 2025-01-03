package ru.kata.springsecurityweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.model.Role;
import ru.kata.springsecurityweb.model.User;
import ru.kata.springsecurityweb.repository.RoleRepository;
import ru.kata.springsecurityweb.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        return userDTO;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = getUserById(userDTO.getId());
        if (user != null) {
            if (!user.getFirstName().equals(userDTO.getFirstName())) {
                user.setFirstName(userDTO.getFirstName());
            }
            if (!user.getLastName().equals(userDTO.getLastName())) {
                user.setLastName(userDTO.getLastName());
            }
            if (!user.getEmail().equals(userDTO.getEmail())) {
                user.setEmail(userDTO.getEmail());
            }
            if (!userDTO.getPassword().isEmpty()) {
                String newPassword = passwordEncoder.encode(userDTO.getPassword());
                if (!user.getPassword().equals(newPassword)) {
                    user.setPassword(newPassword);
                }
            }
            if (userDTO.getRoles() != null) {
                List<String> newRoles = userDTO.getRoles();
                Set<Role> roles = userDTO.getRoles().stream()
                        .map(roleRepository::findByName)
                        .collect(Collectors.toSet());
                user.setRoles(roles);
            }
            userRepository.save(user);
        } else {
            User newUser = new User();
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setEmail(userDTO.getEmail());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            if (userDTO.getRoles() != null) {
                Set<Role> roles = userDTO.getRoles().stream()
                        .map(roleRepository::findByName)
                        .collect(Collectors.toSet());
                newUser.setRoles(roles);
            }
            userRepository.save(newUser);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
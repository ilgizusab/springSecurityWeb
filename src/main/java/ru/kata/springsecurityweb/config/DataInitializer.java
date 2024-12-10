package ru.kata.springsecurityweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.springsecurityweb.model.Role;
import ru.kata.springsecurityweb.model.User;
import ru.kata.springsecurityweb.repository.RoleRepository;
import ru.kata.springsecurityweb.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import java.util.Set;

@Component
public class DataInitializer {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        // Добавляем роли, если их нет
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        // Добавляем пользователей, если их нет
        User user = userRepository.findByUsername("user");
        if (user == null) {
            user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setFirstName("User");
            user.setLastName("User");
            user.setEmail("user@example.com");
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }

        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);
        }
    }
}
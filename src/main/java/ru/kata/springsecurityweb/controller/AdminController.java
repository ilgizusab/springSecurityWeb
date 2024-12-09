package ru.kata.springsecurityweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.model.Role;
import ru.kata.springsecurityweb.model.User;
import ru.kata.springsecurityweb.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles().stream().map(Role::getName).toList());
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin/editUser";
    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
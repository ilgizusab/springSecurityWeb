package ru.kata.springsecurityweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.model.User;
import ru.kata.springsecurityweb.service.UserService;

@Controller
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userService.getUserDTOByUsername(user.getUsername());
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "admin/editUser";
    }

    @PostMapping("/admin/users/edit")
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
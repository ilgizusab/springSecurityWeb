package ru.kata.springsecurityweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public String profile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = userService.getUserDTOByUsername(auth.getName());
        model.addAttribute("userDTO", userDTO);
        return "user/profile";
    }

    @PostMapping("/user/profile")
    public String updateProfile(@ModelAttribute("userDTO") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/";
    }
}
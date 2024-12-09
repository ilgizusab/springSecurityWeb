package ru.kata.springsecurityweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.springsecurityweb.dto.UserDTO;
import ru.kata.springsecurityweb.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            UserDTO userDTO = userService.getUserDTOByUsername(auth.getName());
            model.addAttribute("user", userDTO);
        }
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
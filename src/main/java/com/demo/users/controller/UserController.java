package com.demo.users.controller;

import com.demo.users.model.User;
import com.demo.users.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, Model model) {
        userService.addUser(user);
        return "redirect:/user/index";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        System.out.println("Updating... " +user);
        userService.updateUser(user);
        return "redirect:/user/index";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/cancel")
    public String cancel(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "redirect:/user/index";
    }

    @GetMapping("/reset")
    public String reset() {
        return "redirect:/user/index";
    }
}

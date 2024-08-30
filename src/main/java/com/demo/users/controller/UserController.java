package com.demo.users.controller;

import com.demo.users.model.*;
import com.demo.users.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final EmploymentService employmentService;
    private final LevelService levelService;
    private final PositionService positionService;

    public UserController(UserService userService, EmploymentService employmentService, LevelService levelService, PositionService positionService) {
        this.userService = userService;
        this.employmentService = employmentService;
        this.levelService = levelService;
        this.positionService = positionService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("employments", employmentService.getAllEmployments());
        model.addAttribute("positions", positionService.getAllPositions());
        return "user";
    }

    @GetMapping("/account")
    public String account(Model model) {
        model.addAttribute("authUser", userService.getAuthUser());
        return "account";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam Long level, @RequestParam Long position, Model model) {
        Employment employment = new Employment();
        employment.setLevel(levelService.getLevelById(level));
        employment.setPosition(positionService.getPositionById(position));
        employment.setStatus("Employed");
        employmentService.addEmployment(employment);
        user.setEmployment(employment);
        userService.addUser(user);
        return "redirect:/user/index";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        System.out.println("Updating... " +user);
        userService.updateUser(user);
        return "redirect:/user/account";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/user/index";
    }
}

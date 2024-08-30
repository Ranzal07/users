package com.demo.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.demo.users.service.*;

@Controller
@RequestMapping("/employment")
public class EmploymentController {

    private final UserService userService;
    private final EmploymentService employmentService;
    private final LevelService levelService;
    private final PositionService positionService;

    public EmploymentController(UserService userService, EmploymentService employmentService, LevelService levelService, PositionService positionService) {
        this.userService = userService;
        this.employmentService = employmentService;
        this.levelService = levelService;
        this.positionService = positionService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("authUser", userService.getAuthUser());
        model.addAttribute("levels", levelService.getAllLevels());
        model.addAttribute("positions", positionService.getAllPositions());
        return "employment";
    }

    @PostMapping("/update")
    public String updateEmployment(@RequestParam Long user, Long level, Long position) {
        employmentService.updateEmployment(userService.getUserById(user),levelService.getLevelById(level),positionService.getPositionById(position));
        return "redirect:/employment/index";
    }
}

package com.demo.users.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.demo.users.model.*;
import com.demo.users.service.*;

@Controller
@RequestMapping("/level")
public class LevelController {

    private final UserService userService;
    private final LevelService levelService;

    public LevelController(UserService userService, LevelService levelService) {
        this.userService = userService;
        this.levelService = levelService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("levels", levelService.getAllLevels());
        model.addAttribute("authUser", userService.getAuthUser());
        return "level";
    }

    @PostMapping("/add")
    public String addLevel(@ModelAttribute Level level, Model model) {
        levelService.addLevel(level);
        return "redirect:/level/index";
    }

    @PostMapping("/update")
    public String updateLevel(@ModelAttribute Level level) {
        levelService.updateLevel(level);
        return "redirect:/level/index";
    }

    @PostMapping("/delete")
    public String deleteLevelById(@RequestParam Long id) {
        levelService.deleteLevelById(id);
        return "redirect:/level/index";
    }
}

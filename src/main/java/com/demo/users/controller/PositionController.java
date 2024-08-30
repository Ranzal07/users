package com.demo.users.controller;

import java.util.List;

// import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.demo.users.model.*;
import com.demo.users.service.*;

@Controller
@RequestMapping("/position")
public class PositionController {

    private final UserService userService;
    private final PositionService positionService;

    public PositionController(UserService userService, PositionService positionService) {
        this.userService = userService;
        this.positionService = positionService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("positions", positions);
        model.addAttribute("authUser", userService.getAuthUser());
        return "position";
    }

    @PostMapping("/add")
    public String addPosition(@ModelAttribute Position position, Model model) {
        positionService.addPosition(position);
        return "redirect:/position/index";
    }

    @PostMapping("/update")
    public String updatePosition(@ModelAttribute Position position) {
        positionService.updatePosition(position);
        return "redirect:/position/index";
    }

    @PostMapping("/delete")
    public String deletePositionById(@RequestParam Long id) {
        positionService.deletePositionById(id);
        return "redirect:/position/index";
    }
}

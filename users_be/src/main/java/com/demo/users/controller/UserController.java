package com.demo.users.controller;

import com.demo.users.model.*;
import com.demo.users.payload.UsersResponse;
import com.demo.users.service.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final EmploymentService employmentService;
    private final LevelService levelService;
    private final PositionService positionService;

    @GetMapping("/index")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UsersResponse> usersResponse = userService.getAllUsers();
            return ResponseEntity.ok(usersResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get all users: " + e.getMessage());
        }
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

    @PutMapping("/update")
    public ResponseEntity<?> updateAuthUser(@RequestBody User requestData) {
        try {
            userService.updateUser(requestData);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update user: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUserById(id);
        return "redirect:/user/index";
    }
}

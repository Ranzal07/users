package com.demo.users.controller;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.users.model.EmploymentRequest;
import com.demo.users.service.*;

import lombok.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employment")
public class EmploymentController {

    private final UserService userService;
    private final EmploymentService employmentService;
    private final LevelService levelService;
    private final PositionService positionService;

    @GetMapping("/index")
    public ResponseEntity<?> getAllEmployments() {
        try {
            var response = new HashMap<String, Object>();
            response.put("users", userService.getAllUsers());
            response.put("levels", levelService.getAllLevels());
            response.put("positions", positionService.getAllPositions());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get all employments: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployment(@RequestBody EmploymentRequest requestData) {
        try {
            var user = requestData.getUser();
            var level = requestData.getLevel();
            var position = requestData.getPosition();
            employmentService.updateEmployment(userService.getUserById(user),levelService.getLevelById(level),positionService.getPositionById(position));
            return ResponseEntity.ok("Employment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update employment: " + e.getMessage());
        }
    }
}

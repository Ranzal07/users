package com.demo.users.controller;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.users.payload.EmploymentRequest;
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
            response.put("employments", employmentService.getAllEmployments());
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
            Long userId = requestData.getUserId();
            Long levelId = requestData.getLevelId();
            Long positionId = requestData.getPositionId();
            employmentService.updateEmployment(userService.getUserById(userId),levelService.getLevelById(levelId),positionService.getPositionById(positionId));
            return ResponseEntity.ok("Employment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update employment: " + e.getMessage());
        }
    }
}

package com.demo.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.users.model.*;
import com.demo.users.service.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/level")
public class LevelController {

    private final LevelService levelService;

    @GetMapping("/index")
    public ResponseEntity<?> getAllLevels() {
        try {
            List<Level> levels = levelService.getAllLevels();
            return ResponseEntity.ok(levels);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get all levels: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLevel(@RequestBody Level requestData) {
        try {
            levelService.addLevel(requestData);
            return ResponseEntity.ok("Level added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add level: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateLevel(@RequestBody Level requestData) {
        try {
            levelService.updateLevel(requestData);
            return ResponseEntity.ok("Level updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update level: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        try {
            levelService.deleteLevelById(id);
            return ResponseEntity.ok("Level deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete level: " + e.getMessage());
        }
    }
}

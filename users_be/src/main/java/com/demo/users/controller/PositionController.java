package com.demo.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

// import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.demo.users.model.*;
import com.demo.users.service.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/position")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("/index")
    public ResponseEntity<?> getAllPositions() {
        try {
            List<Position> positions = positionService.getAllPositions();
            return ResponseEntity.ok(positions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to get all positions: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPosition(@RequestBody Position requestData) {
        try {
            positionService.addPosition(requestData);
            return ResponseEntity.ok("Position added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add position: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePosition(@RequestBody Position requestData) {
        try {
            positionService.updatePosition(requestData);
            return ResponseEntity.ok("Position updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update position: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        try {
            positionService.deletePositionById(id);
            return ResponseEntity.ok("Position deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete position: " + e.getMessage());
        }
    }
}

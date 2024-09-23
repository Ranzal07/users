package com.demo.users.service;

import com.demo.users.model.Position;
import com.demo.users.repository.PositionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public void addPosition(Position position) {
        positionRepository.save(position);
    }

    public void updatePosition(Position position) {
        Position existingPosition = positionRepository.findById(position.getId()).orElse(null);
        existingPosition.setName(position.getName());
        existingPosition.setSalary(position.getSalary());
        existingPosition.setRole(position.getRole());
        positionRepository.save(existingPosition);
    }

    public void deletePositionById(Long id) {
        positionRepository.deleteById(id);
    }

    public Position getPositionById(Long id) {
        return positionRepository.findById(id).orElse(null);
    }
}

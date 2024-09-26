package com.demo.users.service;

import com.demo.users.model.Position;
import com.demo.users.payload.PositionsResponse;
import com.demo.users.repository.PositionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public List<PositionsResponse> getAllPositions() {
        List<PositionsResponse> positionsResponse = PositionsResponse.fromPositionList(positionRepository.findAll());
        PositionsResponse emptyPosition = PositionsResponse.builder()
            .id(0L)
            .name(null)
            .salary(null)
            .role(null)
            .build();
        positionsResponse.add(emptyPosition);
        return positionsResponse;
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

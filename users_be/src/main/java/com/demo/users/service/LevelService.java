package com.demo.users.service;

import com.demo.users.model.Level;
import com.demo.users.payload.LevelsResponse;
import com.demo.users.repository.LevelRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelService {
    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public List<LevelsResponse> getAllLevels() {
        List<LevelsResponse> levelsResponse = LevelsResponse.fromLevelList(levelRepository.findAll());
        return levelsResponse;
    }

    public void addLevel(Level position) {
        levelRepository.save(position);
    }

    public void updateLevel(Level position) {
        Level existingLevel = levelRepository.findById(position.getId()).orElse(null);
        existingLevel.setName(position.getName());
        existingLevel.setSalaryRate(position.getSalaryRate());
        levelRepository.save(existingLevel);
    }

    public void deleteLevelById(Long id) {
        levelRepository.deleteById(id);
    }

    public Level getLevelById(Long id) {
        return levelRepository.findById(id).orElse(null);
    }
}

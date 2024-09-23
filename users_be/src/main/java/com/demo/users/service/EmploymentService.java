package com.demo.users.service;

import com.demo.users.model.*;
import com.demo.users.repository.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmploymentService {
    private final EmploymentRepository employmentRepository;

    public EmploymentService(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    public List<Employment> getAllEmployments() {
        return employmentRepository.findAll();
    }

    public void addEmployment(Employment position) {
        employmentRepository.save(position);
    }

    public void updateEmployment(Employment position) {
        Employment existingEmployment = employmentRepository.findById(position.getId()).orElse(null);
        existingEmployment.setLevel(position.getLevel());
        existingEmployment.setPosition(position.getPosition());
        existingEmployment.setStatus(position.getStatus());
        employmentRepository.save(existingEmployment);
    }

    public void deleteEmploymentById(Long id) {
        employmentRepository.deleteById(id);
    }

    public Employment getEmploymentById(Long id) {
        return employmentRepository.findById(id).orElse(null);
    }

    public void updateEmployment(User user, Level level, Position position) {
        Employment employment = user.getEmployment();
        employment.setLevel(level);
        employment.setPosition(position);
        employmentRepository.save(employment);
    }
}

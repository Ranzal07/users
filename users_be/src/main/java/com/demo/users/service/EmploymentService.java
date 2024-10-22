package com.demo.users.service;

import com.demo.users.model.*;
import com.demo.users.payload.EmploymentsResponse;
import com.demo.users.repository.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmploymentService {
    private final EmploymentRepository employmentRepository;
    private final UserRepository userRepository;

    public List<EmploymentsResponse> getAllEmployments(Long authUserId) {
        User authUser = userRepository.findById(authUserId).orElse(null);
        List<EmploymentsResponse> employmentsResponse = EmploymentsResponse.fromEmploymentList(userRepository.findAll(), authUser);
        return employmentsResponse;
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

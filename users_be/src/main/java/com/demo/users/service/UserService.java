package com.demo.users.service;

import com.demo.users.model.*;
import com.demo.users.payload.UsersResponse;
import com.demo.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UsersResponse> getAllUsers() {
        List<UsersResponse> usersResponse = UsersResponse.fromUserList(userRepository.findAll());
        return usersResponse;
    }

    public void addUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String uName = user.getFirstName().toLowerCase();
        user.setUsername(uName);
        user.setPassword(encoder.encode("pass"));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        userRepository.save(existingUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

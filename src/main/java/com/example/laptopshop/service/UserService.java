package com.example.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.domain.dto.RegisterDTO;
import com.example.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserByEmail(String email) {
        List<User> users = this.userRepository.findByEmail(email);
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public User saveUser(User user) {
        User eric = this.userRepository.save(user);
        return eric;
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;
    }

    public boolean checkEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }
}

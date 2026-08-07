package com.example.laptopshop.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.RoleRepository;
import com.example.laptopshop.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo Role ADMIN nếu chưa có
            if (roleRepository.findByName("ADMIN") == null) {
                com.example.laptopshop.domain.Role adminRole = new com.example.laptopshop.domain.Role();
                adminRole.setName("ADMIN");
                adminRole.setDescription("Quản trị viên");
                roleRepository.save(adminRole);
            }

            // Tạo Role USER nếu chưa có
            if (roleRepository.findByName("USER") == null) {
                com.example.laptopshop.domain.Role userRole = new com.example.laptopshop.domain.Role();
                userRole.setName("USER");
                userRole.setDescription("Người dùng");
                roleRepository.save(userRole);
            }

            String email = "admin@gmail.com";
            var users = userRepository.findByEmail(email);
            if (users == null || users.isEmpty()) {
                User user = new User();
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("123456"));
                user.setFullName("Le Trong Phap");
                user.setRole(roleRepository.findByName("ADMIN"));
                userRepository.save(user);
                System.out.println(">>> Đã tạo tài khoản ADMIN mặc định: admin@gmail.com / 123456");
            }
        };
    }
}

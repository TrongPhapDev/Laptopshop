package com.example.laptopshop.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> Đang đăng nhập với email: " + username);
        User user = this.userService.getUserByEmail(username);
        if (user == null) {
            System.out.println(">>> KHÔNG tìm thấy người dùng: " + username);
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + username);
        }
        System.out
                .println(">>> Tìm thấy người dùng: " + user.getEmail() + " | Password trong DB: " + user.getPassword());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())) // Tạm thời để
                                                                                                          // quyền
        // USER
        );
    }
}

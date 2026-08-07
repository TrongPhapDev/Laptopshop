package com.example.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.laptopshop.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);

    void deleteById(Long id);

    List<User> findByEmail(String email);

    boolean existsByEmail(String email);

}

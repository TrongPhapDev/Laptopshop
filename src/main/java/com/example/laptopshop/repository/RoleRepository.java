package com.example.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.laptopshop.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // dùng khi cần tìm role theo tên (ADMIN / USER)
    Role findByName(String name);
}

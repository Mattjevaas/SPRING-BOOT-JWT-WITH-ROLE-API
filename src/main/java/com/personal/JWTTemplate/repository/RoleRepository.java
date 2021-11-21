package com.personal.JWTTemplate.repository;

import com.personal.JWTTemplate.entity.Role;
import com.personal.JWTTemplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String username);
}
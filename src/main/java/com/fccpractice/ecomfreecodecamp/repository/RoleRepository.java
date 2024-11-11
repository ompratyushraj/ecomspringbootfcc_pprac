package com.fccpractice.ecomfreecodecamp.repository;

import com.fccpractice.ecomfreecodecamp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}

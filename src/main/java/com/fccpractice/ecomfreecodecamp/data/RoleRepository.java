package com.fccpractice.ecomfreecodecamp.data;

import com.fccpractice.ecomfreecodecamp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}

package com.fccpractice.ecomfreecodecamp.data;

import com.fccpractice.ecomfreecodecamp.model.Role;
import com.fccpractice.ecomfreecodecamp.model.User;
import com.fccpractice.ecomfreecodecamp.repository.RoleRepository;
import com.fccpractice.ecomfreecodecamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExists(defaultRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            System.out.println("ROLE_USER not found");
            return;
        }

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));

            // Ensure the Role is managed by fetching it from the DB again
            if (userRole.getId() == 0) {
                roleRepository.save(userRole); // In case it's detached, save it first
            }

            user.setRoles(Set.of(userRole)); // Associate the role with the user

            userRepository.save(user); // Persist the user with the roles
            System.out.println("Default user " + i + " created successfully.");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            System.out.println("ROLE_ADMIN not found");
            return;
        }

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }

            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole)); // Set the admin role

            userRepository.save(user); // Save the admin user
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role) == null)  // Check if the role exists
                .map(Role::new)  // Create a new Role object
                .forEach(roleRepository::save);  // Only save new roles
    }
}

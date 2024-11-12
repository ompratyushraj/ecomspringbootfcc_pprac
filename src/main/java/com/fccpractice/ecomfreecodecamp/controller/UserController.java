package com.fccpractice.ecomfreecodecamp.controller;

import com.fccpractice.ecomfreecodecamp.dto.UserDto;
import com.fccpractice.ecomfreecodecamp.exceptions.AlreadyExistsException;
import com.fccpractice.ecomfreecodecamp.exceptions.ResourceNotFoundException;
import com.fccpractice.ecomfreecodecamp.request.CreateUserRequest;
import com.fccpractice.ecomfreecodecamp.request.UserUpdateRequest;
import com.fccpractice.ecomfreecodecamp.response.ApiResponse;
import com.fccpractice.ecomfreecodecamp.service.user.IUserService;
import com.fccpractice.ecomfreecodecamp.model.User;
import com.fccpractice.ecomfreecodecamp.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    private final JwtUtils jwtUtils; // Inject JwtUtils

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);

            // Generate token after user creation
            String token = jwtUtils.generateTokenForUser(user);

            // Return token along with user data in the response
            return ResponseEntity.ok(new ApiResponse("Create User Success!", token));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(request, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

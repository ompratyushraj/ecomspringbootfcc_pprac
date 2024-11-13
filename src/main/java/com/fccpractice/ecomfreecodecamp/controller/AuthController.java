package com.fccpractice.ecomfreecodecamp.controller;

import com.fccpractice.ecomfreecodecamp.model.User;
import com.fccpractice.ecomfreecodecamp.request.LoginRequest;
import com.fccpractice.ecomfreecodecamp.response.ApiResponse;
import com.fccpractice.ecomfreecodecamp.response.JwtResponse;
import com.fccpractice.ecomfreecodecamp.security.jwt.JwtUtils;
import com.fccpractice.ecomfreecodecamp.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));

            // Set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Extract ShopUserDetails from authentication
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

            // Get the User from ShopUserDetails (this is where we fix the casting issue)
            User user = userDetails.getUser(); // Get the User object from ShopUserDetails

            // Generate the JWT token using the User object
            String jwt = jwtUtils.generateTokenForUser(user);

            // Create JwtResponse
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
}

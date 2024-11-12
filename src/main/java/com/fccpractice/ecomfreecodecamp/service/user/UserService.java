package com.fccpractice.ecomfreecodecamp.service.user;

import com.fccpractice.ecomfreecodecamp.dto.UserDto;
import com.fccpractice.ecomfreecodecamp.exceptions.AlreadyExistsException;
import com.fccpractice.ecomfreecodecamp.exceptions.ResourceNotFoundException;
import com.fccpractice.ecomfreecodecamp.model.User;
import com.fccpractice.ecomfreecodecamp.repository.UserRepository;
import com.fccpractice.ecomfreecodecamp.request.CreateUserRequest;
import com.fccpractice.ecomfreecodecamp.request.UserUpdateRequest;
import com.fccpractice.ecomfreecodecamp.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;  // Inject JwtUtils

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user = userRepository.save(user);

                    // Generate JWT after user creation
                    String token = jwtUtils.generateTokenForUser(user);

                    // Optionally, you can return the user and the token or the user with token information
                    return user;
                })
                .orElseThrow(() -> new AlreadyExistsException("Oops !" + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}

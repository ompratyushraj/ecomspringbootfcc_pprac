package com.fccpractice.ecomfreecodecamp.service.user;

import com.fccpractice.ecomfreecodecamp.dto.UserDto;
import com.fccpractice.ecomfreecodecamp.model.User;
import com.fccpractice.ecomfreecodecamp.request.CreateUserRequest;
import com.fccpractice.ecomfreecodecamp.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    User getAuthenticatedUser();
}
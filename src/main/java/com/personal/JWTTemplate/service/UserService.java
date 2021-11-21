package com.personal.JWTTemplate.service;

import com.personal.JWTTemplate.error.NotFoundException;
import com.personal.JWTTemplate.model.request.AddRoleRequest;
import com.personal.JWTTemplate.model.request.CreateUserRequest;
import com.personal.JWTTemplate.model.response.UserResponse;

public interface UserService {
    UserResponse saveUser(CreateUserRequest createUserRequest);
    UserResponse getUser(String username) throws NotFoundException;
    UserResponse addRoleToUser(AddRoleRequest addRoleRequest) throws NotFoundException;
}

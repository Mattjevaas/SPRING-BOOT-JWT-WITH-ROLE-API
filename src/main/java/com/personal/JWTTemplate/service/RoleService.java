package com.personal.JWTTemplate.service;

import com.personal.JWTTemplate.model.request.CreateRoleRequest;
import com.personal.JWTTemplate.model.response.ListRoleResponse;
import com.personal.JWTTemplate.model.response.RoleResponse;

public interface RoleService {
    RoleResponse saveRole(CreateRoleRequest createRoleRequest);
    ListRoleResponse getRoles();
}

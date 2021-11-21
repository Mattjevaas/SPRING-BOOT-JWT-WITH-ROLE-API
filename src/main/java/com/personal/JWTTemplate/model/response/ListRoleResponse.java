package com.personal.JWTTemplate.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListRoleResponse {
    public List<RoleResponse> roles;
}

package com.personal.JWTTemplate.model.response;

import com.personal.JWTTemplate.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private int id;
    private String email;
    private String username;
    private Collection<Role> roles;
}

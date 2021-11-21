package com.personal.JWTTemplate.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleRequest {

    @NotBlank
    public String username;

    @NotBlank
    public String rolename;

}

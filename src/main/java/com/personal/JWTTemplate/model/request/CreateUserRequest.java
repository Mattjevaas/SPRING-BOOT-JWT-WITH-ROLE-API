package com.personal.JWTTemplate.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank @Email
    public String email;
    @NotBlank
    public String username;
    @NotBlank
    @Size(min = 5)
    public String password;
}

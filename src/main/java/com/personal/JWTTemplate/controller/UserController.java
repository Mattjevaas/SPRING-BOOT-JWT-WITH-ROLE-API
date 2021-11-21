package com.personal.JWTTemplate.controller;

import com.personal.JWTTemplate.error.NotFoundException;
import com.personal.JWTTemplate.model.request.AddRoleRequest;
import com.personal.JWTTemplate.model.request.CreateUserRequest;
import com.personal.JWTTemplate.model.response.UserResponse;
import com.personal.JWTTemplate.model.response.WebResponse;
import com.personal.JWTTemplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(
            value = "/users",
            produces = "application/json",
            consumes = "application/json"
    )

    public WebResponse createUser(@RequestBody CreateUserRequest body){
        UserResponse userResponse = userService.saveUser(body);

        return new WebResponse() {{
            code = 200;
            status = "OK";
            data = userResponse;
        }};
    }

    @PostMapping(
            value = "/users/addrole",
            produces = "application/json",
            consumes = "application/json"
    )
    public WebResponse createUser(@RequestBody AddRoleRequest body) throws NotFoundException {
        UserResponse userResponse = userService.addRoleToUser(body);
        return new WebResponse() {{
            code = 200;
            status = "OK";
            data = userResponse;
        }};
    }

    @GetMapping(
            value = "/api/users/{username}",
            produces = "application/json",
            consumes = "application/json"
    )
    public WebResponse getUser(@PathVariable("username") String username) throws NotFoundException {
        UserResponse userResponse = userService.getUser(username);

        return new WebResponse() {{
            code = 200;
            status = "OK";
            data = userResponse;
        }};
    }

}

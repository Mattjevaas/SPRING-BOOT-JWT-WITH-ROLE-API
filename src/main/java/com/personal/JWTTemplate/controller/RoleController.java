package com.personal.JWTTemplate.controller;


import com.personal.JWTTemplate.model.request.CreateRoleRequest;
import com.personal.JWTTemplate.model.response.ListRoleResponse;
import com.personal.JWTTemplate.model.response.RoleResponse;
import com.personal.JWTTemplate.model.response.WebResponse;
import com.personal.JWTTemplate.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping(
            value = "/roles",
            produces = "application/json",
            consumes = "application/json"
    )
    public WebResponse createRole(@RequestBody CreateRoleRequest body){
        RoleResponse roleResponse = roleService.saveRole(body);

        return new WebResponse() {{
            code = 200;
            status = "OK";
            data = roleResponse;
        }};
    }

    @GetMapping(
            value = "/roles/all",
            produces = "application/json",
            consumes = "application/json"
    )
    public WebResponse getAllResponse(){
        ListRoleResponse listRoleResponse = roleService.getRoles();

        return new WebResponse() {{
            code = 200;
            status = "OK";
            data = listRoleResponse;
        }};
    }
}

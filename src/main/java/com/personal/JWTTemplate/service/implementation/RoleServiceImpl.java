package com.personal.JWTTemplate.service.implementation;

import com.personal.JWTTemplate.validation.ValidationUtil;
import com.personal.JWTTemplate.entity.Role;
import com.personal.JWTTemplate.model.request.CreateRoleRequest;
import com.personal.JWTTemplate.model.response.ListRoleResponse;
import com.personal.JWTTemplate.model.response.RoleResponse;
import com.personal.JWTTemplate.repository.RoleRepository;
import com.personal.JWTTemplate.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ValidationUtil validationUtil;

    @Override
    public RoleResponse saveRole(CreateRoleRequest createRoleRequest) {
        validationUtil.validate(createRoleRequest);

        final Role role = new Role(0, createRoleRequest.name);
        final Role roleResult = roleRepository.save(role);

        return new RoleResponse(
                roleResult.getId(),
                roleResult.getName()
        );
    }

    @Override
    public ListRoleResponse getRoles() {
        List<RoleResponse> rolesResponse = new ArrayList<>();
        final List<Role> roles = roleRepository.findAll();

        roles.forEach(roleResult -> rolesResponse.add(new RoleResponse(
                roleResult.getId(),
                roleResult.getName()
        )));

        return new ListRoleResponse(rolesResponse);
    }
}

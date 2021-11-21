package com.personal.JWTTemplate.service.implementation;

import com.personal.JWTTemplate.error.NotFoundException;
import com.personal.JWTTemplate.model.request.AddRoleRequest;
import com.personal.JWTTemplate.validation.ValidationUtil;
import com.personal.JWTTemplate.entity.Role;
import com.personal.JWTTemplate.entity.User;
import com.personal.JWTTemplate.model.request.CreateUserRequest;
import com.personal.JWTTemplate.model.response.UserResponse;
import com.personal.JWTTemplate.repository.RoleRepository;
import com.personal.JWTTemplate.repository.UserRepository;
import com.personal.JWTTemplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(CreateUserRequest createUserRequest) {

        validationUtil.validate(createUserRequest);

        final User user = new User(
                0,
                createUserRequest.email,
                createUserRequest.username,
                passwordEncoder.encode(createUserRequest.password),
                new ArrayList<>()
        );

        User userResult = userRepository.save(user);

        return new UserResponse(
                userResult.getId(),
                userResult.getEmail(),
                userResult.getUsername(),
                userResult.getRoles()
        );
    }

    @Override
    public UserResponse getUser(String username) throws NotFoundException {
        final User userResult = userRepository.findByUsername(username);

        if(userResult == null){
            throw new NotFoundException();
        }

        return new UserResponse(
                userResult.getId(),
                userResult.getEmail(),
                userResult.getUsername(),
                userResult.getRoles()
        );
    }

    @Override
    public UserResponse addRoleToUser(AddRoleRequest addRoleRequest) throws NotFoundException {

        validationUtil.validate(addRoleRequest);
        final User user = userRepository.findByUsername(addRoleRequest.username);
        final Role role = roleRepository.findByName(addRoleRequest.rolename);

        if(user == null || role == null){
            throw new NotFoundException();
        }

        user.getRoles().add(role);

        return getUser(addRoleRequest.username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
             throw new UsernameNotFoundException("User" + username + "not found in database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}

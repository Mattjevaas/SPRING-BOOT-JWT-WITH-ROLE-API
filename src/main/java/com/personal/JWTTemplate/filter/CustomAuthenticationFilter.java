package com.personal.JWTTemplate.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.JWTTemplate.model.response.WebResponse;
import com.personal.JWTTemplate.tokenutil.TokenUtil;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, TokenUtil tokenUtil) {
        this.authenticationManager = authenticationManager;
        this.tokenUtil = tokenUtil;
    }


    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username, password;

        Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
        username = requestMap.get("username");
        password = requestMap.get("password");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User)authResult.getPrincipal();

        String accessToken = tokenUtil.createAccessToken(user, request.getRequestURL().toString());
        String refreshToken = tokenUtil.createRefreshToken(user.getUsername(), request.getRequestURL().toString(), accessToken);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        WebResponse webResponse = new WebResponse(){{
            code = 200;
            status = "OK";
            data = tokens;
        }};
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), webResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        WebResponse webResponse = new WebResponse(){{
            code = 401;
            status = "Unauthorized";
            data = "Failed to Login";
        }};
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(401);
        new ObjectMapper().writeValue(response.getOutputStream(), webResponse);
    }

}

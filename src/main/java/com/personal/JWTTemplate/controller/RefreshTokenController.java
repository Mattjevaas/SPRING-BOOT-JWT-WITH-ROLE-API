package com.personal.JWTTemplate.controller;

import com.personal.JWTTemplate.error.NotFoundException;
import com.personal.JWTTemplate.model.request.RefreshTokenRequest;
import com.personal.JWTTemplate.model.response.TokenResponse;
import com.personal.JWTTemplate.model.response.WebResponse;
import com.personal.JWTTemplate.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping(
            value = "/api/refresh",
            produces = "application/json",
            consumes = "application/json"
    )
    public WebResponse refreshToken(@RequestBody RefreshTokenRequest body, HttpServletRequest request, HttpServletResponse response) throws NotFoundException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        TokenResponse tokenResponse = refreshTokenService.refreshToken(body,authorizationHeader,request.getRequestURL().toString());

        if(tokenResponse != null) {
            return new WebResponse() {{
                code = 200;
                status = "OK";
                data = tokenResponse;
            }};
        } else{
            response.setStatus(401);
            return new WebResponse() {{
                code = 401;
                status = "Unauthorized";
                data = "Invalid Refresh Token";
            }};
        }
    }
}

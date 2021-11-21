package com.personal.JWTTemplate.service;

import com.personal.JWTTemplate.model.request.RefreshTokenRequest;
import com.personal.JWTTemplate.model.response.TokenResponse;

public interface RefreshTokenService {
    TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, String authorizationHeader, String requestUrir);
}

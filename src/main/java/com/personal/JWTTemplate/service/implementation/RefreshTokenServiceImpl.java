package com.personal.JWTTemplate.service.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.personal.JWTTemplate.entity.User;
import com.personal.JWTTemplate.model.request.RefreshTokenRequest;
import com.personal.JWTTemplate.model.response.TokenResponse;
import com.personal.JWTTemplate.repository.UserRepository;
import com.personal.JWTTemplate.service.RefreshTokenService;
import com.personal.JWTTemplate.tokenutil.TokenUtil;
import com.personal.JWTTemplate.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final ValidationUtil validationUtil;

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, String authorizationHeader, String requestUri) {
        validationUtil.validate(refreshTokenRequest);

        String token = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring("Bearer ".length());
        }

        DecodedJWT decodedJWT = tokenUtil.decodedJWT(authorizationHeader);
        String previousToken = decodedJWT.getClaim("previous_token").asString();

        if (Objects.equals(previousToken, token)) {

            String username = decodedJWT.getSubject();
            User user = userRepository.findByUsername(username);

            String accessToken = tokenUtil.createAccessTokenForRefresh(user, requestUri);
            String refreshToken = tokenUtil.createRefreshToken(user.getUsername(), requestUri, accessToken);

            return new TokenResponse(accessToken, refreshToken);
        }

        return null;
    }
}

package com.personal.JWTTemplate.tokenutil;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.personal.JWTTemplate.entity.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;


@Component
public class TokenUtil {

    @Value("${accessTokenExpiredme}")
    private int accessTokenExpired;
    @Value("${refreshTokenExpired}")
    private int refreshTokenExpired;

    private final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public String createAccessToken(User user, String requestUri){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpired * 60 * 1000))
                .withIssuer(requestUri)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String createAccessTokenForRefresh(com.personal.JWTTemplate.entity.User user, String requestUri){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpired * 60 * 1000))
                .withIssuer(requestUri)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String  createRefreshToken(String username, String requestUri, String previousToken){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpired * 60 * 1000))
                .withIssuer(requestUri)
                .withClaim("previous_token", previousToken)
                .sign(algorithm);
    }

    public DecodedJWT decodedJWT(String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

}

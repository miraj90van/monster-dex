package com.assignment.demo.security;

import com.assignment.demo.entity.model.User;
import com.assignment.demo.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.*;

@Component
public class SecurityService implements Serializable {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String defaultExpirationTimeInSecondsConf;

    public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TokenInfo generateAccessToken(User user) {
        var claims = new HashMap<String, Object>() {{
            put("role", user.getRoles());
        }};

        return doGenerateToken(claims, user.getUsername(), String.valueOf(user.getId()));
    }

    private TokenInfo doGenerateToken(Map<String, Object> claims, String issuer, String subject) {
        var expirationTimeInMilliseconds = Long.parseLong(defaultExpirationTimeInSecondsConf) * 1000;
        var expirationDate = new Date(new Date().getTime() + expirationTimeInMilliseconds);

        return doGenerateToken(expirationDate, claims, issuer, subject);
    }

    private TokenInfo doGenerateToken(Date expirationDate, Map<String, Object> claims, String issuer, String subject) {
        var createdDate = new Date();
        var token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

        return TokenInfo.builder()
                .token(token)
                .issuedAt(createdDate)
                .expiresAt(expirationDate)
                .build();
    }

    public Mono<TokenInfo> authenticate(String username, String password) {
        return userRepository.getUserByUsername(username)
                .flatMap(user -> {
                    if (!user.isEnabled())
                        return Mono.error(new AuthException("Account disabled.", "USER_ACCOUNT_DISABLED"));

                    if (!passwordEncoder.encode(password).equals(user.getPassword()))
                        return Mono.error(new AuthException("Invalid user password!", "INVALID_USER_PASSWORD"));

                    return Mono.just(generateAccessToken(user).toBuilder()
                            .userId(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new AuthException("Invalid user, " + username + " is not registered.", "INVALID_USERNAME")));
    }
}

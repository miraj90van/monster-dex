package com.assignment.demo.controller;

import com.assignment.demo.entity.request.UserLoginRequest;
import com.assignment.demo.entity.response.UserLoginResponse;
import com.assignment.demo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@Valid @RequestBody UserLoginRequest request) {
        return securityService.authenticate(request.getUsername(), request.getPassword())
                .flatMap(tokenInfo -> Mono.just(ResponseEntity.ok(UserLoginResponse.builder()
                        .userId(tokenInfo.getUserId())
                        .token(tokenInfo.getToken())
                        .issuedAt(tokenInfo.getIssuedAt())
                        .expiresAt(tokenInfo.getExpiresAt())
                        .build())));
    }
}
package com.assignment.demo.security;

import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.model.User;
import com.assignment.demo.security.auth.UnauthorizedException;
import com.assignment.demo.security.auth.UserPrincipal;
import com.assignment.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId())
                .map(BaseResponse::getData)
                .filter(Objects::nonNull)
                .filter(User::isEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User account is disabled.")))
                .map(user -> authentication);
    }
}

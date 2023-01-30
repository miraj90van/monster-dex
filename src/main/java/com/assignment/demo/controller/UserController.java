package com.assignment.demo.controller;

import com.assignment.demo.constans.ApiPath;
import com.assignment.demo.entity.model.User;
import com.assignment.demo.entity.request.UserAddRequest;
import com.assignment.demo.entity.response.UserAddResponse;
import com.assignment.demo.helper.ResponseHelper;
import com.assignment.demo.security.auth.UserPrincipal;
import com.assignment.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPath.USER_URL)
    public Mono<ResponseEntity<UserAddResponse>> saveUser(@Valid  @RequestBody UserAddRequest request) {
        return userService.saveUser(request)
                .map(ResponseHelper::getResponseData);
    }

    @GetMapping(ApiPath.USER_URL)
    public Mono<ResponseEntity<User>> getUserById(Authentication authentication) {
        final UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return userService.getUserById(principal.getId())
                .map(ResponseHelper::getResponseData);
    }
}
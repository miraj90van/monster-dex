package com.assignment.demo.service;

import com.assignment.demo.constans.ResponseType;
import com.assignment.demo.entity.model.BaseResponse;
import com.assignment.demo.entity.model.User;
import com.assignment.demo.entity.request.UserAddRequest;
import com.assignment.demo.entity.response.UserAddResponse;
import com.assignment.demo.exceptions.BusinessLogicException;
import com.assignment.demo.helper.TimeHelper;
import com.assignment.demo.repository.UserRepository;
import com.assignment.demo.security.SecurityService;
import com.assignment.demo.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final TransactionalOperator operator;

    public Mono<BaseResponse<UserAddResponse>> saveUser(UserAddRequest request){

        return findExistUserName(request)
                .filter(aBoolean -> aBoolean)
                .flatMap(aBoolean -> savingUser(request))
                .map(securityService::generateAccessToken)
                .as(operator::transactional)
                .map(this::buildUserAddResponse)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("user with name has been existed, request: {}", request);
                    return Mono.just(new BaseResponse<>(ResponseType.USERNAME_ALREADY_EXISTS));
                }));
    }

    private Mono<User> savingUser(UserAddRequest request){
        final User user = buildUser(request);
        return userRepository.save(user)
                .onErrorResume(throwable -> {
                    log.error("error when process save user : {}", throwable.getMessage());
                    return Mono.error(new BusinessLogicException(ResponseType.INTERNAL_SERVER_ERROR));
                });
    }

    public Mono<Boolean> findExistUserName(UserAddRequest request){
        return userRepository.getUserByUsername(request.getUsername())
                .filter(Objects::nonNull)
                .map(user ->  false)
                .switchIfEmpty(Mono.defer(() -> Mono.just(true)));
    }

    public Mono<BaseResponse<User>> getUserById(long id){
        return userRepository.findById(id)
                .map(userResult -> new BaseResponse<>(ResponseType.SUCCESS, userResult))
                .onErrorResume(throwable -> {
                    log.error("error when process get user : {}", throwable.getMessage());
                    return Mono.just(new BaseResponse<>(ResponseType.INVALID));
                });
    }

    private BaseResponse<UserAddResponse> buildUserAddResponse(TokenInfo tokenInfo){
        final UserAddResponse userAddResponse = UserAddResponse.builder()
                .token(tokenInfo.getToken())
                .build();

        return new BaseResponse<>(ResponseType.SUCCESS_CREATE_USER, userAddResponse);
    }

    private User buildUser(UserAddRequest request){
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .enabled(true)
                .createdDate(TimeHelper.getLocalDateTimeNow())
                .roles("ROLE_USER")
                .build();
    }
}
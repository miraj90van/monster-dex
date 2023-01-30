package com.assignment.demo.security.support;

import com.assignment.demo.security.auth.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

@Slf4j
public class JwtVerifyHandler {
    private final String secret;

    public JwtVerifyHandler(String secret) {
        this.secret = secret;
    }

    public Mono<VerificationResult> check(String accessToken) {
        return verify(accessToken)
                .onErrorResume(e -> {
                    log.error("got error when parsing jwt message: {}", e.getMessage(), e);
                    return Mono.error(new UnauthorizedException(e.getMessage()));
                });
    }

    private Mono<VerificationResult> verify(String token) {
        return Mono.create(sink -> {
            VerificationResult verificationResult = Try.of(() -> {
                Claims claims = getAllClaimsFromToken(token);
                final Date expiration = claims.getExpiration();

                if (expiration.before(new Date())){
                    sink.error(new UnauthorizedException("Token expired"));
                }

                return new VerificationResult(claims, token);
            }).onFailure(sink::error).get();
            sink.success(verificationResult);
        });


    }

    public Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

    public static class VerificationResult {
        public Claims claims;
        public String token;

        public VerificationResult(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
}


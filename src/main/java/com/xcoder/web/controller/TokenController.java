package com.xcoder.web.controller;

import com.xcoder.security.SecurityService;
import com.xcoder.web.dto.PasswordTokenRequest;
import com.xcoder.web.dto.RefreshTokenRequest;
import com.xcoder.web.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/public/token")
@RequiredArgsConstructor
public class TokenController {
    private final SecurityService securityService;

    @PostMapping("/password")
    public Mono<ResponseEntity<TokenResponse>> password(@RequestBody PasswordTokenRequest request) {
        return securityService.processPasswordToken(request.getUsername(), request.getPassword())
            .map(it -> ResponseEntity.ok(new TokenResponse(it.token(), it.refreshToken())));
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<TokenResponse>> refresh(@RequestBody RefreshTokenRequest request) {
        return securityService.processRefreshToken(request.getRefreshToken())
            .map(it -> ResponseEntity.ok(new TokenResponse(it.token(), it.refreshToken())));
    }
}
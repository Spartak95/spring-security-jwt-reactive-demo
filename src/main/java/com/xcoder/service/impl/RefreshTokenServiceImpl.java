package com.xcoder.service.impl;

import java.time.Duration;
import java.util.UUID;

import com.xcoder.entity.RefreshToken;
import com.xcoder.exception.RefreshTokenException;
import com.xcoder.repository.RefreshTokenRepository;
import com.xcoder.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${user-service.jwt.refreshTokenExpiration}")
    private Duration refreshTokenExpiration;

    @Override
    public Mono<RefreshToken> save(String userId) {
        String refreshTokenValue = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(id, userId, refreshTokenValue);

        return refreshTokenRepository.save(refreshToken, refreshTokenExpiration)
            .filter(isSuccess -> isSuccess)
            .flatMap(ignore -> Mono.just(refreshToken))
            .switchIfEmpty(Mono.error(new RefreshTokenException("Error on save refresh token for userId: " + userId)));
    }

    @Override
    public Mono<RefreshToken> getByValue(String refreshToken) {
        return refreshTokenRepository.getByValue(refreshToken);
    }
}

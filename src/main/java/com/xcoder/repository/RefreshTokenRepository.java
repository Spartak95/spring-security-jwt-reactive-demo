package com.xcoder.repository;

import java.time.Duration;

import com.xcoder.entity.RefreshToken;
import reactor.core.publisher.Mono;

public interface RefreshTokenRepository {
    Mono<Boolean> save(RefreshToken refreshToken, Duration expTime);

    Mono<RefreshToken> getByValue(String refreshToken);
}

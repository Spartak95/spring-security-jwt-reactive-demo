package com.xcoder.service;

import com.xcoder.entity.RefreshToken;
import reactor.core.publisher.Mono;

public interface RefreshTokenService {
    Mono<RefreshToken> save(String userId);

    Mono<RefreshToken> getByValue(String refreshToken);
}

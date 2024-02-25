package com.xcoder.service;

import com.xcoder.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> create(User user);

    Mono<User> findByUsername(String username);

    Mono<User> findById(String id);
}

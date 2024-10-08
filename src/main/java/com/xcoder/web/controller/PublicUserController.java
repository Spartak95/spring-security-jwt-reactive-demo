package com.xcoder.web.controller;

import java.util.stream.Collectors;

import com.xcoder.entity.RoleType;
import com.xcoder.entity.User;
import com.xcoder.service.UserService;
import com.xcoder.web.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {
    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<String>> createUser(@RequestBody CreateUserRequest request) {
        return userService.create(getUser(request))
            .map(user -> ResponseEntity.ok("User successfully created!"));
    }

    private User getUser(CreateUserRequest request) {
        return User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .username(request.getUsername())
            .roles(request.getRoles().stream()
                       .map(it -> RoleType.valueOf(it.toUpperCase()))
                       .collect(Collectors.toSet()))
            .build();
    }
}
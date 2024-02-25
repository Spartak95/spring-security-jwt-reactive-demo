package com.xcoder.web.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
}

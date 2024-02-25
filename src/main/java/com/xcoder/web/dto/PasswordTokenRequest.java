package com.xcoder.web.dto;

import lombok.Data;

@Data
public class PasswordTokenRequest {
    private String username;
    private String password;
}

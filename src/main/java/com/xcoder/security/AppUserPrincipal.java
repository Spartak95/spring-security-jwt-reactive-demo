package com.xcoder.security;

import java.security.Principal;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppUserPrincipal implements Principal {
    private final String name;
    private final String id;
    private final List<String> roles;

    @Override
    public String getName() {
        return null;
    }
}

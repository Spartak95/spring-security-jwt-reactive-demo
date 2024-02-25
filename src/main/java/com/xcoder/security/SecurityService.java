package com.xcoder.security;

import com.xcoder.entity.User;
import com.xcoder.exception.CheckPasswordException;
import com.xcoder.security.jwt.TokenService;
import com.xcoder.service.RefreshTokenService;
import com.xcoder.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public Mono<TokenData> processPasswordToken(String username, String password) {
        return userService.findByUsername(username)
            .flatMap(user -> {
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    log.error("Exception trying to check password for user: {}", username);
                    return Mono.error(new CheckPasswordException());
                }

                return createTokenData(user);
            });
    }

    private Mono<TokenData> createTokenData(User user) {
        String token = tokenService.generateToken(user.getUsername(),
                                                  user.getId(),
                                                  user.getRoles().stream().map(Enum::name).toList());

        return refreshTokenService.save(user.getId())
            .flatMap(refreshToken -> Mono.just(new TokenData(token, refreshToken.getValue())));
    }

    public Mono<TokenData> processRefreshToken(String refreshTokenValue) {
        return refreshTokenService.getByValue(refreshTokenValue)
            .flatMap(refreshToken -> userService.findById(refreshToken.getUserId()))
            .flatMap(this::createTokenData);
    }

    public record TokenData(String token, String refreshToken) {

    }
}

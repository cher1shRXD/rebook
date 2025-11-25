package me.cher1shrxd.rebook.global.security.jwt.dto;

public record JwtResponse(
        String accessToken,
        String refreshToken
) {
}

package me.cher1shrxd.rebook.domain.auth.dto.request;

public record SignupRequest(
        String username,
        String email,
        String nickname,
        String password
) {
}

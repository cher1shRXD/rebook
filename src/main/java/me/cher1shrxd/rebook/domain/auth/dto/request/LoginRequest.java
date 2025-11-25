package me.cher1shrxd.rebook.domain.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}

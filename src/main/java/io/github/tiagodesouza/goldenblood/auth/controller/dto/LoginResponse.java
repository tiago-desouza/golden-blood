package io.github.tiagodesouza.goldenblood.auth.controller.dto;

public record LoginResponse(String token, long expiresIn) {
}

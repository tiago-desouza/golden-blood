package io.github.tiagodesouza.goldenblood.auth.controller.dto;

import java.util.List;

public record UserInfoResponse(Long id, String username, String email, List<String> roles) {
}

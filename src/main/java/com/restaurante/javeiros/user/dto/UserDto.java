package com.restaurante.javeiros.user.dto;

import com.restaurante.javeiros.user.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserDto(
        @Schema(description = "User id")
        Long id,
        @Schema(description = "User name")
        String name,
        @Schema(description = "User email")
        String email,
        @Schema(description = "User login")
        String login,
        @Schema(description = "User password")
        String password,
        @Schema(description = "User role")
        RoleName role,
        @Schema(description = "User address")
        String address) {
}

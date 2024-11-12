package com.restaurante.javeiros.user.dto;

import com.restaurante.javeiros.user.enums.RoleName;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserDto(
        @Schema(description = "User name to be created")
        String name,
        @Schema(description = "User email to be created")
        String email,
        @Schema(description = "User login to be created")
        String login,
        @Schema(description = "User password to be created")
        String password,
        @Schema(description = "User role to be created")
        RoleName role,
        @Schema(description = "User address to be created")
        String address
) {
}

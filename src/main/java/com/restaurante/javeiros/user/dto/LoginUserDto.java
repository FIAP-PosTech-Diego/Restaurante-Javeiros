package com.restaurante.javeiros.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginUserDto(

        @Schema(description = "User name to log in")
        String email,
        @Schema(description = "User password to log in")
        String password
) {
}

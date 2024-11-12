package com.restaurante.javeiros.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RecoveryJwtTokenDto(

        @Schema(description = "Token")
        String token

) {
}

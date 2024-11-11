package com.restaurante.javeiros.user.dto;

import com.restaurante.javeiros.user.enums.RoleName;

public record RecoveryUserDto(

        Long id,

        String email,

        RoleName role
) {
}

package com.restaurante.javeiros.dto;

import com.restaurante.javeiros.enums.RoleName;

public record CreateUserDto(
        String email,
        String password,
        RoleName role
) {
}

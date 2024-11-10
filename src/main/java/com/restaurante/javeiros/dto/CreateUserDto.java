package com.restaurante.javeiros.dto;

import com.restaurante.javeiros.enums.RoleName;

public record CreateUserDto(
        String name,
        String email,
        String login,
        String password,
        RoleName role,
        String address
) {
}

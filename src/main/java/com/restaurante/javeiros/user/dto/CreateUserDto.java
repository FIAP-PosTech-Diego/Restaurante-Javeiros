package com.restaurante.javeiros.user.dto;

import com.restaurante.javeiros.user.enums.RoleName;

public record CreateUserDto(
        String name,
        String email,
        String login,
        String password,
        RoleName role,
        String address
) {
}

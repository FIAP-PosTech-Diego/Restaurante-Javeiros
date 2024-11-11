package com.restaurante.javeiros.user.dto;

import com.restaurante.javeiros.user.enums.RoleName;

public record UserDto(
        Long id,
        String name,
        String email,
        String login,
        String password,
        RoleName role,
        String address) {
}

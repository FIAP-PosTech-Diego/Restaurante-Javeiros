package com.restaurante.javeiros.dto;

import com.restaurante.javeiros.enums.RoleName;

import java.util.List;

public record RecoveryUserDto(

        Long id,

        String email,

        RoleName role
) {
}

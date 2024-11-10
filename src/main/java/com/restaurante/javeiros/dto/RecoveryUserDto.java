package com.restaurante.javeiros.dto;

import com.restaurante.javeiros.entitities.Role;

import java.util.List;

public record RecoveryUserDto(

        Long id,

        String email,

        List<Role> roles
) {
}

package com.restaurante.javeiros.user.controller;


import com.restaurante.javeiros.user.dto.CreateUserDto;
import com.restaurante.javeiros.user.dto.LoginUserDto;
import com.restaurante.javeiros.user.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.user.dto.UserDto;
import com.restaurante.javeiros.user.entitities.User;
import com.restaurante.javeiros.exception.HttpStatusProject;
import com.restaurante.javeiros.user.exception.UserException;
import com.restaurante.javeiros.user.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        log.info("Login user - email: {}", loginUserDto.email());
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        log.info("Creating user - email: {}", createUserDto.email());
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        log.info("Updating user - id: {}", userDto.id());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        if (!authenticatedUser.getId().equals(userDto.id())) {
            log.error("User authenticated is different from the request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.updateUser(userDto);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updatePassword(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {
        log.info("Updating password user - id: {}", userId);

        if (!newPassword.equals(confirmNewPassword)) {
            log.error("Password and confirm password are different");
            throw new UserException("Passwords are different", HttpStatusProject.VALIDATION);
        }

        userService.updatePassword(userId, currentPassword, newPassword);

        return ResponseEntity.ok().build();
    }


}
package com.restaurante.javeiros.user.controller;


import com.restaurante.javeiros.user.dto.CreateUserDto;
import com.restaurante.javeiros.user.dto.LoginUserDto;
import com.restaurante.javeiros.user.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.user.dto.UserDto;
import com.restaurante.javeiros.user.entitities.User;
import com.restaurante.javeiros.exception.HttpStatusProject;
import com.restaurante.javeiros.user.exception.UserException;
import com.restaurante.javeiros.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        if (!authenticatedUser.getId().equals(userDt.id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        userService.updateUser(userDt);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updatePassword(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {

        if (!newPassword.equals(confirmNewPassword)) {
            throw new UserException("Passwords are different", HttpStatusProject.VALIDATION);
        }

        userService.updatePassword(userId, currentPassword, newPassword);

        return ResponseEntity.ok().build();
    }


}
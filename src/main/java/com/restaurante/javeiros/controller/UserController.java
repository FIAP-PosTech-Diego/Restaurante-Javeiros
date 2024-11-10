package com.restaurante.javeiros.controller;


import com.restaurante.javeiros.dto.CreateUserDto;
import com.restaurante.javeiros.dto.LoginUserDto;
import com.restaurante.javeiros.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.dto.UserDto;
import com.restaurante.javeiros.entitities.User;
import com.restaurante.javeiros.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

//    @PutMapping("/update-user")
//    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto){
//        userService.updateUser(userDto);
//        return ResponseEntity.ok().build();
//    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDt) {
        // Use Spring Security to get the authenticated user from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        // Verify that the authenticated user is the same as the user being updated
        if (!authenticatedUser.getId().equals(userDt.id())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Update the user information
        userService.updateUser(userDt);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Void> updatePassword(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {
        try {
            // Validates and updates the password for the authenticated user.
            if (!newPassword.equals(confirmNewPassword)) {
            }

            userService.updatePassword(userId, currentPassword, newPassword);
        } catch (Exception e) {
            // Handles exceptions during password update process.
        }
        return ResponseEntity.ok().build();
    }


}
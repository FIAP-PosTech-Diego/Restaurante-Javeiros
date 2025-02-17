package com.restaurante.javeiros.user.controller;


import com.restaurante.javeiros.user.dto.CreateUserDto;
import com.restaurante.javeiros.user.dto.LoginUserDto;
import com.restaurante.javeiros.user.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.user.dto.UserDto;
import com.restaurante.javeiros.user.entitities.User;
import com.restaurante.javeiros.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController("UserController")
@RequestMapping("/user")
@Slf4j
@Tag(name = "User", description = "Controller to authorization and crud user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(
            description = "Login user using email and password",
            summary = "Login user",
            responses = {
                    @ApiResponse(description = "Token", responseCode = "200")
            }
    )
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        log.info("Login user - email: {}", loginUserDto.email());
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping
    @Operation(
            description = "Create a new user with provided details",
            summary = "Create a user",
            responses = {
                    @ApiResponse(description = "User created successfully", responseCode = "201"),
                    @ApiResponse(description = "Bad Request - Invalid data", responseCode = "400")
            }
    )
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        log.info("Creating user - email: {}", createUserDto.email());
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    @Operation(
            description = "Update the user details",
            summary = "Update user information",
            responses = {
                    @ApiResponse(description = "User updated successfully", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized - User mismatch", responseCode = "401"),
                    @ApiResponse(description = "Bad Request - Invalid user data", responseCode = "400")
            }
    )
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
    @Operation(
            description = "Update the password for a user",
            summary = "Update user password",
            responses = {
                    @ApiResponse(description = "Password updated successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad Request - Passwords do not match", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized - Invalid current password", responseCode = "401"),
                    @ApiResponse(description = "User not found", responseCode = "404")
            }
    )
    public ResponseEntity<Void> updatePassword(
            @RequestParam Long userId,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmNewPassword) {
        log.info("Updating password user - id: {}", userId);

        userService.updatePassword(userId, currentPassword, newPassword, confirmNewPassword);

        return ResponseEntity.ok().build();
    }


}
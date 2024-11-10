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

    @PutMapping("/update-user")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return ResponseEntity.ok().build();
    }

}
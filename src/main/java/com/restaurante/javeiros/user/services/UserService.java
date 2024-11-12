package com.restaurante.javeiros.user.services;


import com.restaurante.javeiros.user.dto.CreateUserDto;
import com.restaurante.javeiros.user.dto.LoginUserDto;
import com.restaurante.javeiros.user.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.user.dto.UserDto;
import com.restaurante.javeiros.user.entitities.User;
import com.restaurante.javeiros.exception.HttpStatusProject;
import com.restaurante.javeiros.user.exception.UserException;
import com.restaurante.javeiros.user.repositories.UserRepository;
import com.restaurante.javeiros.security.authentication.JwtTokenService;
import com.restaurante.javeiros.security.config.SecurityConfiguration;
import com.restaurante.javeiros.security.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

       return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    @Transactional
    public void createUser(CreateUserDto createUserDto) {

        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .login(createUserDto.login())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .role(createUserDto.role())
                .address(createUserDto.address())
                .updatedDate(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
    }

    public List<User> getAll() {

        return userRepository.findAll();

    }

    @Transactional
    public void updateUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.id())
                .name(userDto.name())
                .email(userDto.email())
                .login(userDto.login())
                .password(securityConfiguration.passwordEncoder().encode(userDto.password()))
                .role(userDto.role())
                .address(userDto.address())
                .updatedDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public void updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.error("Password encoder does not match with current password");
            throw new UserException("Current password is incorrect", HttpStatusProject.VALIDATION);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}

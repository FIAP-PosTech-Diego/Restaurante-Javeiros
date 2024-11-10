package com.restaurante.javeiros.services;


import com.restaurante.javeiros.dto.CreateUserDto;
import com.restaurante.javeiros.dto.LoginUserDto;
import com.restaurante.javeiros.dto.RecoveryJwtTokenDto;
import com.restaurante.javeiros.dto.UserDto;
import com.restaurante.javeiros.entitities.Role;
import com.restaurante.javeiros.entitities.User;
import com.restaurante.javeiros.repositories.UserRepository;
import com.restaurante.javeiros.security.authentication.JwtTokenService;
import com.restaurante.javeiros.security.config.SecurityConfiguration;
import com.restaurante.javeiros.security.userdetails.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
       return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    @Transactional
    public void createUser(CreateUserDto createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .login(createUserDto.login())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .address(createUserDto.address())
                .updatedDate(LocalDateTime.now())
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }

    public List<User> getAll() {

        return userRepository.findAll();

    }

    @Transactional
    public void updateUser(UserDto userDto) {
        User user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .login(userDto.login())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(userDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(userDto.role()).build()))
                .address(userDto.address())
                .updatedDate(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }
}

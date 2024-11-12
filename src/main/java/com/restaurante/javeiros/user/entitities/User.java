package com.restaurante.javeiros.user.entitities;

import com.restaurante.javeiros.user.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "users")
@Entity(name = "USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(unique = true, name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleName role;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "address")
    private String address;

}

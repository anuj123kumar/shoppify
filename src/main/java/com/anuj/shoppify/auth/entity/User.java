package com.anuj.shoppify.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated primary key
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true) // Email should be unique and not null
    @Email // Validates email format
    private String email;

    @NotNull
    @Column(nullable = false) // Password cannot be null
    private String password;

}

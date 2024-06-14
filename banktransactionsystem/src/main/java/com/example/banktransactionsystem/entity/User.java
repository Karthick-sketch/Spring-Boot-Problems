package com.example.banktransactionsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NonNull
    @Column(name = "password", nullable = false)
    private String password;
}

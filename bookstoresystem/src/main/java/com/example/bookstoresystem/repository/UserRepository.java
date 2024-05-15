package com.example.bookstoresystem.repository;

import com.example.bookstoresystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

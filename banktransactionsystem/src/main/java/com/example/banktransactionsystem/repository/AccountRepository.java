package com.example.banktransactionsystem.repository;

import com.example.banktransactionsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUserId(int userId);
}

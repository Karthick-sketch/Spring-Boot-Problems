package com.example.banktransactionsystem.repository;

import com.example.banktransactionsystem.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}

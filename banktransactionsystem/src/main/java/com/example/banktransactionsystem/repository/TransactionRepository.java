package com.example.banktransactionsystem.repository;

import com.example.banktransactionsystem.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Map;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
//    @Query(value = "SELECT COUNT(*) total_transactions, SUM(CASE WHEN transaction_type = 'withdraw' THEN amount ELSE 0 END) total_withdrawn_amount, SUM(CASE WHEN transaction_type = 'deposit' THEN amount ELSE 0 END) total_deposited_amount FROM transactions WHERE transaction_date = ?", nativeQuery = true)
//    Map<String, Object> transactionSummary(LocalDate transactionDate);
}

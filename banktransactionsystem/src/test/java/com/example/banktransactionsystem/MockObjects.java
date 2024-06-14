package com.example.banktransactionsystem;

import com.example.banktransactionsystem.dto.TransactionDTO;
import com.example.banktransactionsystem.dto.TransactionSummaryDTO;
import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.entity.Transaction;

import java.time.LocalDate;
import java.util.Map;

public class MockObjects {
    static Account getMockAccount() {
        Account account = new Account();
        account.setAccountId(1);
        account.setAccountHolderName("Kratos");
        account.setCurrentBalance(15_000);
        return account;
    }

    static Account getUpdatedMockAccount() {
        Account account = getMockAccount();
        account.setCurrentBalance(10_000);
        return account;
    }

    static Transaction getMockTransaction(String transactionType) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setAccountId(1);
        transaction.setAmount(5000);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(LocalDate.now());
        return transaction;
    }

    static TransactionDTO getMockTransactionDTO() {
        return new TransactionDTO(1, 5000, "withdraw", "Do transaction");
    }

    static TransactionSummaryDTO getMockTransactionSummaryDTO(int totalTransactions, int totalWithdrawnAmount, int totalDepositedAmount) {
        TransactionSummaryDTO transactionSummaryDTO = new TransactionSummaryDTO();
        transactionSummaryDTO.setTotalTransactions(totalTransactions);
        transactionSummaryDTO.setTotalWithdrawnAmount(totalWithdrawnAmount);
        transactionSummaryDTO.setTotalDepositedAmount(totalDepositedAmount);
        return transactionSummaryDTO;
    }

    static Map<String, Object> getMockTransactionSummaryMap(int totalTransactions, int totalWithdrawnAmount, int totalDepositedAmount) {
        return Map.of("total_transactions", totalTransactions, "total_withdrawn_amount", totalWithdrawnAmount, "total_deposited_amount", totalDepositedAmount);
    }
}

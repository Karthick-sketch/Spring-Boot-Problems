package com.example.banktransactionsystem.service;

import com.example.banktransactionsystem.dto.TransactionDTO;
import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.entity.Transaction;
import com.example.banktransactionsystem.exception.BadRequestException;
import com.example.banktransactionsystem.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountService accountService;

    /*public TransactionSummaryDTO transactionSummary(LocalDate transactionDate) {
        Map<String, Object> summary = transactionRepository.transactionSummary(transactionDate);
        return new TransactionSummaryDTO(
                (int) summary.get("total_transactions"),
                (int) summary.get("total_withdrawn_amount"),
                (int) summary.get("total_deposited_amount")
        );
    }*/

    public Transaction withdrawAmount(int userId, TransactionDTO transactionDTO) {
        Account account = accountService.getAccountById(transactionDTO.getAccountId());
        if (transactionDTO.getAmount() > account.getCurrentBalance()) {
            throw new BadRequestException("Insufficient balance");
        }
        // reduce amount from current balance
        account.setCurrentBalance(account.getCurrentBalance() - transactionDTO.getAmount());
        accountService.updateAccount(userId, account);
        // create transaction history
        return transactionRepository.save(createTransactionObject(transactionDTO.getAccountId(), transactionDTO.getAmount(), "withdraw"));
    }

    public Transaction depositAmount(int userId, int amount) {
        Account account = accountService.getAccountByUserId(userId);
        // reduce amount from current balance
        account.setCurrentBalance(account.getCurrentBalance() + amount);
        accountService.updateAccount(userId, account);
        // create transaction history
        return transactionRepository.save(createTransactionObject(account.getAccountId(), amount, "deposit"));
    }

    private Transaction createTransactionObject(int accountId, int amount, String type) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setTransactionDate(LocalDate.now());
        return transaction;
    }
}

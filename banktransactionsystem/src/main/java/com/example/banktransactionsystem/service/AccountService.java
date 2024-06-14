package com.example.banktransactionsystem.service;

import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.EntityNotFoundException;
import com.example.banktransactionsystem.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;
    private UserService userService;

    public Account getAccountById(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new EntityNotFoundException("There is no account with the ID of " + accountId);
        }
        return account.get();
    }

    public Account getAccountByUserId(int userId) {
        Optional<Account> account = accountRepository.findByUserId(userId);
        if (account.isEmpty()) {
            throw new EntityNotFoundException("No accounts found");
        }
        return account.get();
    }

    public Account createAccount(int userId, Account account) {
        account.setUser(userService.getUserById(userId));
        return accountRepository.save(account);
    }

    public Account updateAccount(int userId, Account updatedAccount) {
        Account account = getAccountByUserId(userId);
        updatedAccount.setAccountId(account.getAccountId());
        return accountRepository.save(updatedAccount);
    }

    public void deleteAccount(int userId) {
        accountRepository.delete(getAccountByUserId(userId));
    }
}

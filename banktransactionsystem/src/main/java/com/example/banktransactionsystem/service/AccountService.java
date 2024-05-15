package com.example.banktransactionsystem.service;

import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.EntityNotFoundException;
import com.example.banktransactionsystem.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {
    private AccountRepository accountRepository;

    public Account getAccountById(int accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new EntityNotFoundException("There is no account with the ID of " + accountId);
        }
        return account.get();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(int accountId, Account updatedAccount) {
        Account account = getAccountById(accountId);
        updatedAccount.setAccountId(account.getAccountId());
        return accountRepository.save(updatedAccount);
    }

    public void deleteAccount(int accountId) {
        accountRepository.delete(getAccountById(accountId));
    }
}

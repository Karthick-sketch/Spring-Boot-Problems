package com.example.banktransactionsystem;

import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.EntityNotFoundException;
import com.example.banktransactionsystem.repository.AccountRepository;
import com.example.banktransactionsystem.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testGetAccountById() {
        int accountId = 1;
        Account mockAccount = MockObjects.getMockAccount();
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

        Account validAccount = accountService.getAccountById(accountId);
        Executable invalidId = () -> accountService.getAccountById(2);

        Assertions.assertEquals(mockAccount, validAccount);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateAccount() {
        int accountId = 1;
        Account mockAccount = MockObjects.getMockAccount();
        Account updatedMockAccount = MockObjects.getUpdatedMockAccount();
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        Mockito.when(accountRepository.save(updatedMockAccount)).thenReturn(updatedMockAccount);

        Executable invalidId = () -> accountService.getAccountById(2);
        Account validAccount = accountService.updateAccount(accountId, updatedMockAccount);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockAccount, validAccount);
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
    }

    @Test
    public void testDeleteAccount() {
        int accountId = 1;
        Account mockAccount = MockObjects.getMockAccount();
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));
        accountService.deleteAccount(accountId);
        Mockito.verify(accountRepository, Mockito.times(1)).delete(mockAccount);
    }
}

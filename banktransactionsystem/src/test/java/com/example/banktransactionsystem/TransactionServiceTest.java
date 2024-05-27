package com.example.banktransactionsystem;

import com.example.banktransactionsystem.dto.TransactionSummaryDTO;
import com.example.banktransactionsystem.entity.Transaction;
import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.BadRequestException;
import com.example.banktransactionsystem.repository.TransactionRepository;
import com.example.banktransactionsystem.service.TransactionService;
import com.example.banktransactionsystem.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testTransactionSummary() {
        LocalDate transactionDate = LocalDate.now(), invalidTransactionDate = LocalDate.of(2024, 5, 1);

        TransactionSummaryDTO mockTransactionSummaryDTO = MockObjects.getMockTransactionSummaryDTO(2, 10_000, 20_000);
        Map<String, Object> mockTransactionSummaryMap = MockObjects.getMockTransactionSummaryMap(2, 10_000, 20_000);
        TransactionSummaryDTO emptyTransactionSummaryDTO = MockObjects.getMockTransactionSummaryDTO(0, 0, 0);
        Map<String, Object> emptyTransactionSummaryMap = MockObjects.getMockTransactionSummaryMap(0, 0, 0);

        Mockito.when(transactionRepository.transactionSummary(transactionDate)).thenReturn(mockTransactionSummaryMap);
        Mockito.when(transactionRepository.transactionSummary(invalidTransactionDate)).thenReturn(emptyTransactionSummaryMap);

        TransactionSummaryDTO validTransactionSummary = transactionService.transactionSummary(transactionDate);
        TransactionSummaryDTO emptyTransactionSummary = transactionService.transactionSummary(invalidTransactionDate);

        Assertions.assertEquals(mockTransactionSummaryDTO, validTransactionSummary);
        Assertions.assertEquals(emptyTransactionSummaryDTO, emptyTransactionSummary);
    }

    @Test
    public void testWithdrawAmount() {
        int accountId = 1, amount = 5000;
        Account mockAccount = MockObjects.getMockAccount();
        Account mockUpdatedAccount = MockObjects.getUpdatedMockAccount();
        Transaction mockTransaction = MockObjects.getMockTransaction("withdraw");

        Mockito.when(accountService.getAccountById(accountId)).thenReturn(mockAccount);
        Mockito.when(accountService.updateAccount(accountId, mockAccount)).thenReturn(mockUpdatedAccount);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(mockTransaction);

        Executable insufficientBalance = () -> transactionService.withdrawAmount(accountId, 20_000);
        Transaction validTransaction = transactionService.withdrawAmount(accountId, amount);

        Assertions.assertThrows(BadRequestException.class, insufficientBalance);
        Assertions.assertEquals(mockTransaction, validTransaction);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }

    @Test
    public void testDepositAmount() {
        int accountId = 1, amount = 5000;
        Account mockAccount = MockObjects.getMockAccount();
        Account mockUpdatedAccount = MockObjects.getUpdatedMockAccount();
        Transaction mockTransaction = MockObjects.getMockTransaction("deposit");

        Mockito.when(accountService.getAccountById(accountId)).thenReturn(mockAccount);
        Mockito.when(accountService.updateAccount(accountId, mockAccount)).thenReturn(mockUpdatedAccount);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(mockTransaction);

        Transaction validTransaction = transactionService.depositAmount(accountId, amount);
        Assertions.assertEquals(mockTransaction, validTransaction);
        Mockito.verify(transactionRepository, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }
}

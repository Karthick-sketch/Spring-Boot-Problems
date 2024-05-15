package com.example.banktransactionsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDTO {
    private int totalTransactions;
    private int totalWithdrawnAmount;
    private int totalDepositedAmount;
}

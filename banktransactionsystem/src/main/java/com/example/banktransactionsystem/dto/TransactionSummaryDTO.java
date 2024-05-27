package com.example.banktransactionsystem.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryDTO {
    private int totalTransactions;
    private int totalWithdrawnAmount;
    private int totalDepositedAmount;
}

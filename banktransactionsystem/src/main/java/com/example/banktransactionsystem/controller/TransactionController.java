package com.example.banktransactionsystem.controller;

import com.example.banktransactionsystem.dto.TransactionSummaryDTO;
import com.example.banktransactionsystem.entity.Transaction;
import com.example.banktransactionsystem.exception.ErrorResponse;
import com.example.banktransactionsystem.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@AllArgsConstructor
@Tag(name = "Transaction Controller", description = "Withdraw and deposit amounts.")
@RestController
@RequestMapping("/bank")
public class TransactionController {
    private TransactionService transactionService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful amount withdrawal", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful amount withdrawal from an account", content = @Content(schema = @Schema(implementation = Transaction.class)))
    })
    @Operation(summary = "Withdraw amount by account ID", description = "Withdraw amount from an account.")
    @PutMapping(value = "/withdraw/{accountId}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> withdrawAmount(@PathVariable int accountId, @PathVariable int amount) {
        return new ResponseEntity<>(transactionService.withdrawAmount(accountId, amount), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful amount deposit", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful amount deposit to an account", content = @Content(schema = @Schema(implementation = Transaction.class)))
    })
    @Operation(summary = "Deposit amount by account ID", description = "Withdraw amount to an account.")
    @PutMapping(value = "/deposit/{accountId}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> depositAmount(@PathVariable int accountId, @PathVariable int amount) {
        return new ResponseEntity<>(transactionService.depositAmount(accountId, amount), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successful retrieval of Transaction summary.", content = @Content(schema = @Schema(implementation = TransactionSummaryDTO.class)))
    @Operation(summary = "Get Transaction summary by Date", description = "Returns Transactions summary on the given date.")
    @GetMapping(value = "/transaction/summary/{transactionDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionSummaryDTO> getTransactionSummary(@PathVariable LocalDate transactionDate) {
        return new ResponseEntity<>(transactionService.transactionSummary(transactionDate), HttpStatus.OK);
    }
}

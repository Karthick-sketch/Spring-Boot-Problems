package com.example.banktransactionsystem.controller;

import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.ErrorResponse;
import com.example.banktransactionsystem.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.List;

@AllArgsConstructor
@Tag(name = "Account Controller", description = "Create and Retrieve accounts.")
@RestController
@RequestMapping("/bank/account")
public class AccountController {
    private AccountService accountService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of account", content = @Content(schema = @Schema(implementation = Account.class))),
    })
    @Operation(summary = "Get account by ID", description = "Returns a account record based on an ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountById(@PathVariable int id) {
        return new ResponseEntity<>(accountService.getAccountById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successful retrieval of accounts", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Account.class))))
    @Operation(summary = "Retrieves account record", description = "Provides a list of all accounts.")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of account", content = @Content(schema = @Schema(implementation = Account.class)))
    @Operation(summary = "Create account record", description = "Creates a account record from the provided payload.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of a account", content = @Content(schema = @Schema(implementation = Account.class)))
    })
    @Operation(summary = "Update a account by ID", description = "Updates an existing account record from the provided payload and ID.")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@PathVariable int id, @RequestBody Account account) {
        return new ResponseEntity<>(accountService.updateAccount(id, account), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of a account")
    })
    @Operation(summary = "Delete a account by ID", description = "Deletes a account record based on an ID.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

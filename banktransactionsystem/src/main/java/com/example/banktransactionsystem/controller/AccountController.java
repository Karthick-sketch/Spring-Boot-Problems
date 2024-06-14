package com.example.banktransactionsystem.controller;

import com.example.banktransactionsystem.entity.Account;
import com.example.banktransactionsystem.exception.ErrorResponse;
import com.example.banktransactionsystem.security.UserSession;
import com.example.banktransactionsystem.service.AccountService;
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

@AllArgsConstructor
@Tag(name = "Account Controller", description = "Create and Retrieve accounts.")
@RestController
@RequestMapping("/bank/account")
public class AccountController {
    private AccountService accountService;
    private UserSession userSession;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of account", content = @Content(schema = @Schema(implementation = Account.class))),
    })
    @Operation(summary = "Get Account details", description = "Returns the authenticated user's account details.")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountDetails() {
        return new ResponseEntity<>(accountService.getAccountByUserId(userSession.getAuthenticatedUserId()), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of account", content = @Content(schema = @Schema(implementation = Account.class)))
    @Operation(summary = "Create account record", description = "Creates a account record from the provided payload.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        int userId = userSession.getAuthenticatedUserId();
        return new ResponseEntity<>(accountService.createAccount(userId, account), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of a account", content = @Content(schema = @Schema(implementation = Account.class)))
    })
    @Operation(summary = "Update the authenticated user's account details", description = "Updates an existing account record from the provided payload and ID.")
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        int userId = userSession.getAuthenticatedUserId();
        return new ResponseEntity<>(accountService.updateAccount(userId, account), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Account doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of a account")
    })
    @Operation(summary = "Delete a account by ID", description = "Deletes a account record based on an ID.")
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteAccount() {
        accountService.deleteAccount(userSession.getAuthenticatedUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

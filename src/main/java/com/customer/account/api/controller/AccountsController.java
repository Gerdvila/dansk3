package com.customer.account.api.controller;

import com.customer.account.api.model.aggregates.request.CreateAccountRequest;
import com.customer.account.api.model.aggregates.request.DepositBalanceRequest;
import com.customer.account.api.model.aggregates.request.WithdrawBalanceRequest;
import com.customer.account.api.model.aggregates.response.CreateAccountResponse;
import com.customer.account.api.model.aggregates.response.FetchAccountBalanceResponse;
import com.customer.account.api.model.aggregates.response.FetchAccountTransactionsResponse;
import com.customer.account.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
@RequestMapping("/accounts/v1/account")
public class AccountsController {

    private final CreateAccountService createAccountService;
    private final DepositBalanceAccountService depositBalanceAccountService;
    private final WithdrawBalanceAccountService withdrawBalanceAccountService;
    private final FetchAccountBalanceService fetchAccountBalanceService;
    private final FetchAccountTransactionService fetchAccountTransactionService;

    public AccountsController(CreateAccountService createAccountService,
                              DepositBalanceAccountService depositBalanceAccountService,
                              WithdrawBalanceAccountService withdrawBalanceAccountService,
                              FetchAccountBalanceService fetchAccountBalanceService,
                              FetchAccountTransactionService fetchAccountTransactionService) {
        this.createAccountService = createAccountService;
        this.depositBalanceAccountService = depositBalanceAccountService;
        this.withdrawBalanceAccountService = withdrawBalanceAccountService;
        this.fetchAccountBalanceService = fetchAccountBalanceService;
        this.fetchAccountTransactionService = fetchAccountTransactionService;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return createAccountService.createAccount(createAccountRequest);
    }

    @PutMapping("/deposit")
    @ResponseStatus(value = HttpStatus.OK)
    public void deposit(@RequestBody DepositBalanceRequest depositBalanceRequest) {
        depositBalanceAccountService.addBalance(depositBalanceRequest);
    }

    @PutMapping("/withdraw")
    @ResponseStatus(value = HttpStatus.OK)
    public void withdraw(@RequestBody WithdrawBalanceRequest withdrawBalanceRequest) {
        withdrawBalanceAccountService.withdrawBalance(withdrawBalanceRequest);
    }

    @GetMapping("/{accountId}/balance")
    @ResponseStatus(value = HttpStatus.OK)
    public FetchAccountBalanceResponse fetchBalance(@NotBlank(message = "AccountID cannot be null.") @PathVariable("accountId") String accountId) {
        return fetchAccountBalanceService.fetchAccountBalance(accountId);
    }

    @GetMapping("/{accountId}/transactions")
    @ResponseStatus(value = HttpStatus.OK)
    public FetchAccountTransactionsResponse fetchTransactions(@NotBlank(message = "AccountID cannot be null.") @PathVariable("accountId") String accountId){
        return fetchAccountTransactionService.fetchAccountTransactions(accountId);
    }
}
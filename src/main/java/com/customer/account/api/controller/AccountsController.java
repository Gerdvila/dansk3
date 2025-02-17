package com.customer.account.api.controller;

import com.customer.account.api.model.aggregates.request.CreateAccountRequest;
import com.customer.account.api.model.aggregates.response.CreateAccountResponse;
import com.customer.account.service.CreateAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/accounts/v1/account")
public class AccountsController {

    private final CreateAccountService createAccountService;

    public AccountsController(CreateAccountService createAccountService) {
        this.createAccountService = createAccountService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateAccountResponse createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return createAccountService.createAccount(createAccountRequest);
    }


}
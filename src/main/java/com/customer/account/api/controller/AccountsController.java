package com.customer.account.api.controller;

import com.customer.account.service.FetchAccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/accounts/v1/account")
public class AccountsController {

    private final FetchAccountService fetchAccountService;

    public AccountsController(FetchAccountService fetchAccountService) {
        this.fetchAccountService = fetchAccountService;
    }
}
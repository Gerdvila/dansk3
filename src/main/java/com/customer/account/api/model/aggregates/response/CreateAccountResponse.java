package com.customer.account.api.model.aggregates.response;

public class CreateAccountResponse {
    private final String accountId;

    public CreateAccountResponse(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }
}
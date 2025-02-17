package com.customer.account.api.model.aggregates.response;

import java.math.BigDecimal;

public class FetchAccountResponse {
    private final BigDecimal balance;

    public FetchAccountResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
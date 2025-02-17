package com.customer.account.api.model.aggregates.response;

import java.math.BigDecimal;

public class FetchAccountBalanceResponse {
    private final BigDecimal balance;

    public FetchAccountBalanceResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
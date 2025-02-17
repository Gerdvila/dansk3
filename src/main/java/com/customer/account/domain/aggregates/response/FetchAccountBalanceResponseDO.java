package com.customer.account.domain.aggregates.response;

import java.math.BigDecimal;

public class FetchAccountBalanceResponseDO {
    private final BigDecimal balance;

    public FetchAccountBalanceResponseDO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
package com.customer.account.domain.aggregates.response;

import java.math.BigDecimal;

public class AccountResponseDO{
    private final String id;
    private final BigDecimal balance;

    public AccountResponseDO(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
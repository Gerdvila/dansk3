package com.customer.account.domain.aggregates.request;

import java.math.BigDecimal;

public class AddAccountBalanceRequestDO {
    private final String accountId;
    private final BigDecimal amount;

    public AddAccountBalanceRequestDO(String accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
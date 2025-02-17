package com.customer.account.domain.aggregates.response;

import com.customer.account.domain.primitives.enums.TransactionType;

import java.math.BigDecimal;

public class FetchTransactionsResponseDO {
    private final String id;
    private final String accountId;
    private final TransactionType transactionType;
    private final BigDecimal amount;

    public FetchTransactionsResponseDO(String id, String accountId, TransactionType transactionType, BigDecimal amount) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }
}
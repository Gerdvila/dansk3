package com.customer.account.domain.aggregates.request;

import com.customer.account.domain.primitives.enums.TransactionType;

import java.math.BigDecimal;

public class CreateTransactionRequestDO {
    private String id;
    private String accountId;
    private TransactionType transactionType;
    private BigDecimal amount;

    public CreateTransactionRequestDO(String id, String accountId, TransactionType transactionType, BigDecimal amount) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}

package com.customer.account.api.model.aggregates.response;

import com.customer.account.domain.primitives.enums.TransactionType;

import java.math.BigDecimal;

public class TransactionResponse {
    private final String id;
    private final TransactionType transactionType;
    private final BigDecimal amount;

    public TransactionResponse(String id, TransactionType transactionType, BigDecimal amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
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
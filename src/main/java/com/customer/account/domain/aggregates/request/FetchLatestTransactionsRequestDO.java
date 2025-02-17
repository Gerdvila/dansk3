package com.customer.account.domain.aggregates.request;

import com.customer.account.domain.primitives.enums.TransactionType;

public class FetchLatestTransactionsRequestDO {
    private final String accountId;
    private final TransactionType transactionType;

    public FetchLatestTransactionsRequestDO(String accountId, TransactionType transactionType) {
        this.accountId = accountId;
        this.transactionType = transactionType;
    }

    public String getAccountId() {
        return accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
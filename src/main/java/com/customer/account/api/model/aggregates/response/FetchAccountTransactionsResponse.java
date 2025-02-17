package com.customer.account.api.model.aggregates.response;

import java.util.List;

public class FetchAccountTransactionsResponse {
    private final List<TransactionResponse> transactions;

    public FetchAccountTransactionsResponse(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }
}
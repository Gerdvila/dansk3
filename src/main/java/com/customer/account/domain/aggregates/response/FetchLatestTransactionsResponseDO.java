package com.customer.account.domain.aggregates.response;

import java.util.List;

public class FetchLatestTransactionsResponseDO {

    private final List<FetchTransactionResponseDO> transactionResponses;

    public FetchLatestTransactionsResponseDO(List<FetchTransactionResponseDO> transactionResponses){
        this.transactionResponses = transactionResponses;
    }

    public List<FetchTransactionResponseDO> getTransactionResponses() {
        return transactionResponses;
    }
}
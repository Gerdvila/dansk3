package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.CreateTransactionRequestDO;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchLatestTransactionsResponseDO;

public interface TransactionDAO {
    void createTransaction(CreateTransactionRequestDO createTransactionRequestDO);
    FetchLatestTransactionsResponseDO fetchLatestTransactions(FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO);
}

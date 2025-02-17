package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchTransactionsResponseDO;

public interface AccountDAO {
    void createAccount(CreateAccountRequestDO createAccountRequestDO);
    //TODO : think of request object for withdraw/deposit maybe some design pattern to make a single function.
    void deposit();
    void withdraw();
    FetchAccountBalanceResponseDO fetchAccountBalance(String accountId);
    FetchTransactionsResponseDO fetchLatestTransactions(FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO);
}

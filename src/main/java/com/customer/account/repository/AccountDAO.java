package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.AddAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.request.WithdrawAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;

public interface AccountDAO {
    boolean isAccountExist(String accountId);

    void createAccount(CreateAccountRequestDO createAccountRequestDO);

    void addAccountBalance(AddAccountBalanceRequestDO addAccountBalanceRequestDO);

    void withdrawAccountBalance(WithdrawAccountBalanceRequestDO withdrawAccountBalanceRequestDO);

    FetchAccountBalanceResponseDO fetchAccountBalance(String accountId);
}

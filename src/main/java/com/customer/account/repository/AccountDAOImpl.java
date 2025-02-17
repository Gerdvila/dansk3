package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.aggregates.response.FetchTransactionsResponseDO;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void createAccount(CreateAccountRequestDO createAccountRequestDO) {
    }

    public void deposit() {
    }

    public void withdraw() {
    }

    public FetchAccountBalanceResponseDO fetchAccountBalance(String accountId) {
        return null;
    }

    public FetchTransactionsResponseDO fetchLatestTransactions(FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO) {
        return null;
    }
}

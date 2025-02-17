package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.aggregates.response.FetchTransactionsResponseDO;
import com.customer.account.domain.primitives.enums.AccountStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private static final String ACCOUNT_ID = "id";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String ACCOUNT_STATUS = "status";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void createAccount(CreateAccountRequestDO createAccountRequestDO) {
        String query = "INSERT INTO account(id, customer_id, status) VALUES (:id, :customer_id, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, createAccountRequestDO.getAccountId())
                .addValue(CUSTOMER_ID, createAccountRequestDO.getCustomerId())
                .addValue(ACCOUNT_STATUS, AccountStatus.ACTIVE.toString());

        namedParameterJdbcTemplate.update(query, params);
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

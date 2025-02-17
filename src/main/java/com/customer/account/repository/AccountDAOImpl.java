package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.AddAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.request.WithdrawAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.primitives.enums.AccountStatus;
import com.customer.account.repository.mapper.FetchAccountBalanceMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private static final String ACCOUNT_ID = "id";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String ACCOUNT_STATUS = "status";
    private static final String AMOUNT = "amount";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AccountDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean isAccountExist(String accountId) {
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM account\n" +
                "WHERE id = :id\n" +
                ");";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, accountId);

        return namedParameterJdbcTemplate.queryForObject(query, params, Boolean.class);
    }

    @Override
    public void createAccount(CreateAccountRequestDO createAccountRequestDO) {
        String query = "INSERT INTO account(id, customer_id, status) VALUES (:id, :customer_id, :status)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, createAccountRequestDO.getAccountId())
                .addValue(CUSTOMER_ID, createAccountRequestDO.getCustomerId())
                .addValue(ACCOUNT_STATUS, AccountStatus.ACTIVE.toString());

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public FetchAccountBalanceResponseDO fetchAccountBalance(String accountId) {
        String query = "SELECT balance\n" +
                "FROM account" +
                " WHERE id = :id AND status = 'ACTIVE'";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, accountId);

        return namedParameterJdbcTemplate.queryForObject(query, params, new FetchAccountBalanceMapper());
    }

    @Override
    public void addAccountBalance(AddAccountBalanceRequestDO addAccountBalanceRequestDO) {
        String query = "UPDATE account\n" +
                "SET balance = balance + :amount, updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE id = :id AND status = 'ACTIVE'";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, addAccountBalanceRequestDO.getAccountId())
                .addValue(AMOUNT, addAccountBalanceRequestDO.getAmount());

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public void withdrawAccountBalance(WithdrawAccountBalanceRequestDO withdrawAccountBalanceRequestDO) {
        String query = "UPDATE account\n" +
                "SET balance = balance - :amount, updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE id = :id AND status = 'ACTIVE'";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, withdrawAccountBalanceRequestDO.getAccountId())
                .addValue(AMOUNT, withdrawAccountBalanceRequestDO.getAmount());

        namedParameterJdbcTemplate.update(query, params);
    }


}
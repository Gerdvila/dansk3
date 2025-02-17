package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.CreateTransactionRequestDO;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchLatestTransactionsResponseDO;
import com.customer.account.repository.mapper.FetchTransactionsMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAOImpl implements TransactionDAO {

    private static final String TRANSACTION_ID = "id";
    private static final String ACCOUNT_ID = "account_id";
    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final String AMOUNT = "amount";
    private static final String LIMIT = "limit";
    private static final int TRANSACTION_AMOUNT = 10;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TransactionDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void createTransaction(CreateTransactionRequestDO createTransactionRequestDO) {
        String query = "INSERT INTO transaction (id, account_id, transaction_type, amount) " +
                "VALUES (:id, :account_id, :transaction_type, :amount) ";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(TRANSACTION_ID, createTransactionRequestDO.getId())
                .addValue(ACCOUNT_ID, createTransactionRequestDO.getAccountId())
                .addValue(TRANSACTION_TYPE, createTransactionRequestDO.getTransactionType().toString())
                .addValue(AMOUNT, createTransactionRequestDO.getAmount());

        namedParameterJdbcTemplate.update(query, params);
    }

    @Override
    public FetchLatestTransactionsResponseDO fetchLatestTransactions(FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO) {
        String query = "SELECT id, account_id, transaction_type, amount \n" +
                "FROM transaction\n" +
                "WHERE account_id = :account_id\n" +
                "ORDER BY created_at DESC\n" +
                "LIMIT :limit;\n";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(ACCOUNT_ID, fetchLatestTransactionsRequestDO.getAccountId())
                .addValue(LIMIT, TRANSACTION_AMOUNT);

        return new FetchLatestTransactionsResponseDO(
                namedParameterJdbcTemplate.query(query, params, new FetchTransactionsMapper()));
    }
}
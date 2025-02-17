package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchLatestTransactionsResponseDO;
import com.customer.account.repository.mapper.FetchTransactionsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionDAOImplTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private TransactionDAOImpl transactionDAO;

    @Mock
    private FetchTransactionsMapper fetchTransactionsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchLatestTransactions_ShouldReturnTransactions() {
        String accountId = "accountId";
        FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO = new FetchLatestTransactionsRequestDO(accountId, null);
        String query = "SELECT id, account_id, transaction_type, amount\n" +
                "FROM transaction\n" +
                "WHERE account_id = :account_id\n" +
                "ORDER BY created_at DESC\n" +
                "LIMIT :limit;";

        FetchLatestTransactionsResponseDO expectedResponseDO = new FetchLatestTransactionsResponseDO(Collections.emptyList());

        when(namedParameterJdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class), any(FetchTransactionsMapper.class)))
                .thenReturn(expectedResponseDO.getTransactionResponses());

        FetchLatestTransactionsResponseDO response = transactionDAO.fetchLatestTransactions(fetchLatestTransactionsRequestDO);

        assertNotNull(response);
        assertEquals(0, response.getTransactionResponses().size());
        verify(namedParameterJdbcTemplate, times(1)).query(any(String.class), any(MapSqlParameterSource.class), any(FetchTransactionsMapper.class));
    }

    @Test
    void fetchLatestTransactions_WhenNoTransactionsExist_ReturnsEmptyList() {
        String accountId = "accountId";
        FetchLatestTransactionsRequestDO fetchLatestTransactionsRequestDO = new FetchLatestTransactionsRequestDO(accountId, null);
        String query = "SELECT id, account_id, transaction_type, amount\n" +
                "FROM transaction\n" +
                "WHERE account_id = :account_id\n" +
                "ORDER BY created_at DESC\n" +
                "LIMIT :limit;";

        when(namedParameterJdbcTemplate.query(any(String.class), any(MapSqlParameterSource.class), any(FetchTransactionsMapper.class)))
                .thenReturn(Collections.emptyList());

        FetchLatestTransactionsResponseDO response = transactionDAO.fetchLatestTransactions(fetchLatestTransactionsRequestDO);

        assertNotNull(response);
        assertTrue(response.getTransactionResponses().isEmpty());
    }
}
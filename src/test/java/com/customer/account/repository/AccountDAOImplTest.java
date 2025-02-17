package com.customer.account.repository;

import com.customer.account.domain.aggregates.request.AddAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.domain.aggregates.request.WithdrawAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.repository.mapper.FetchAccountBalanceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class AccountDAOImplTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private AccountDAOImpl accountDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isAccountExist_WhenAccountExists_ReturnsTrue() {
        String accountId = "existingAccount";
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM account\n" +
                "WHERE id = :id\n" +
                ");";

        when(namedParameterJdbcTemplate.queryForObject(eq(query), any(MapSqlParameterSource.class), eq(Boolean.class)))
                .thenReturn(true);

        boolean exists = accountDAO.isAccountExist(accountId);

        assertTrue(exists);
    }

    @Test
    void isAccountExist_WhenAccountDoesNotExist_ReturnsFalse() {
        String accountId = "nonExistentAccount";
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM account\n" +
                "WHERE id = :id\n" +
                ");";

        when(namedParameterJdbcTemplate.queryForObject(eq(query), any(MapSqlParameterSource.class), eq(Boolean.class)))
                .thenReturn(false);

        boolean exists = accountDAO.isAccountExist(accountId);

        assertFalse(exists);
    }

    @Test
    void createAccount_WhenCalled_ExecutesInsertQuery() {
        CreateAccountRequestDO createAccountRequestDO = new CreateAccountRequestDO("account123", "customer456");
        String query = "INSERT INTO account(id, customer_id, status) VALUES (:id, :customer_id, :status)";

        accountDAO.createAccount(createAccountRequestDO);

        verify(namedParameterJdbcTemplate, times(1)).update(eq(query), any(MapSqlParameterSource.class));
    }

    @Test
    void fetchAccountBalance_WhenAccountExists_ReturnsBalance() {
        String accountId = "existingAccount";
        FetchAccountBalanceResponseDO expectedResponse = new FetchAccountBalanceResponseDO(BigDecimal.valueOf(500L));
        String query = "SELECT balance\n" +
                "FROM account" +
                " WHERE id = :id AND status = 'ACTIVE'";

        when(namedParameterJdbcTemplate.queryForObject(eq(query), any(MapSqlParameterSource.class), any(FetchAccountBalanceMapper.class)))
                .thenReturn(expectedResponse);

        FetchAccountBalanceResponseDO response = accountDAO.fetchAccountBalance(accountId);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(500L), response.getBalance());
    }

    @Test
    void addAccountBalance_WhenCalled_ExecutesUpdateQuery() {
        AddAccountBalanceRequestDO addRequest = new AddAccountBalanceRequestDO("account123", BigDecimal.valueOf(200L));
        String query = "UPDATE account\n" +
                "SET balance = balance + :amount, updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE id = :id AND status = 'ACTIVE'";

        accountDAO.addAccountBalance(addRequest);

        verify(namedParameterJdbcTemplate, times(1)).update(eq(query), any(MapSqlParameterSource.class));
    }

    @Test
    void withdrawAccountBalance_WhenCalled_ExecutesUpdateQuery() {
        WithdrawAccountBalanceRequestDO withdrawRequest = new WithdrawAccountBalanceRequestDO("account123", BigDecimal.valueOf(50L));
        String query = "UPDATE account\n" +
                "SET balance = balance - :amount, updated_at = CURRENT_TIMESTAMP\n" +
                "WHERE id = :id AND status = 'ACTIVE'";

        accountDAO.withdrawAccountBalance(withdrawRequest);

        verify(namedParameterJdbcTemplate, times(1)).update(eq(query), any(MapSqlParameterSource.class));
    }
}
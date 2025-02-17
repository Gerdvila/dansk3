package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.response.FetchAccountBalanceResponse;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.repository.AccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchAccountBalanceServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private FetchAccountBalanceService fetchAccountBalanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAccountBalance_WhenAccountDoesNotExist_ThrowAccountNotPreviousException() {
        String accountId = "accountId";
        when(accountDAO.fetchAccountBalance(accountId)).thenReturn(null);

        AccountNotPreviousException e = assertThrows(AccountNotPreviousException.class,
                () -> fetchAccountBalanceService.fetchAccountBalance(accountId));

        assertEquals("Account not found", e.getMessage());
    }

    @Test
    void fetchAccountBalance_WhenAccountExists_ReturnsBalance() {
        String accountId = "accountId";
        BigDecimal balance = BigDecimal.valueOf(500L);
        FetchAccountBalanceResponseDO accountBalanceDO = new FetchAccountBalanceResponseDO(balance);

        when(accountDAO.fetchAccountBalance(accountId)).thenReturn(accountBalanceDO);

        FetchAccountBalanceResponse response = fetchAccountBalanceService.fetchAccountBalance(accountId);

        assertNotNull(response);
        assertEquals(balance, response.getBalance());
    }
}
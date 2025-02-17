package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.request.DepositBalanceRequest;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.TransactionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DepositBalanceAccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private TransactionDAO transactionDAO;

    @InjectMocks
    private DepositBalanceAccountService depositBalanceAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBalance_WhenInvalidIdProvided_ThrowAccountNotPreviousException() {
        when(accountDAO.isAccountExist("accountId")).thenReturn(false);

        AccountNotPreviousException e = assertThrows(AccountNotPreviousException.class,
                () -> depositBalanceAccountService.addBalance(new DepositBalanceRequest("accountId", BigDecimal.valueOf(100L))));

        assertEquals("Account is not previous.", e.getMessage());
    }

    @Test
    void addBalance_WhenValidRequest_AddsBalanceAndCreatesTransaction() {
        String accountId = "accountId";
        BigDecimal depositAmount = BigDecimal.valueOf(100L);
        FetchAccountBalanceResponseDO accountBalance = new FetchAccountBalanceResponseDO(BigDecimal.valueOf(200L));

        when(accountDAO.isAccountExist(accountId)).thenReturn(true);
        when(accountDAO.fetchAccountBalance(accountId)).thenReturn(accountBalance);

        DepositBalanceRequest depositBalanceRequest = new DepositBalanceRequest(accountId, depositAmount);

        depositBalanceAccountService.addBalance(depositBalanceRequest);

        verify(transactionDAO, times(1)).createTransaction(any());
    }
}
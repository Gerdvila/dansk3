package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.exceptions.BalanceValidForWithdrawalException;
import com.customer.account.api.model.aggregates.request.WithdrawBalanceRequest;
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

class WithdrawBalanceAccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private TransactionDAO transactionDAO;

    @InjectMocks
    private WithdrawBalanceAccountService withdrawBalanceAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void withDrawBalance_WhenInvalidIdProvided_ThrowAccountNotPreviousException() {
        when(accountDAO.isAccountExist("accountId")).thenReturn(false);

        AccountNotPreviousException e = assertThrows(AccountNotPreviousException.class,
                () -> withdrawBalanceAccountService.withdrawBalance(new WithdrawBalanceRequest("accountId", BigDecimal.valueOf(100L))));

        assertEquals("Account is not previous.", e.getMessage());
    }

    @Test
    void withdrawBalance_WhenBalanceIsTooLow_ThrowBalanceValidForWithdrawalException() {
        String accountId = "accountId";
        BigDecimal withdrawalAmount = BigDecimal.valueOf(200L);
        FetchAccountBalanceResponseDO accountBalance = new FetchAccountBalanceResponseDO(BigDecimal.valueOf(100L));

        when(accountDAO.isAccountExist(accountId)).thenReturn(true);
        when(accountDAO.fetchAccountBalance(accountId)).thenReturn(accountBalance);

        BalanceValidForWithdrawalException e = assertThrows(BalanceValidForWithdrawalException.class,
                () -> withdrawBalanceAccountService.withdrawBalance(new WithdrawBalanceRequest(accountId, withdrawalAmount)));

        assertEquals("Balance is too low to withdraw provided amount", e.getMessage());
    }

    @Test
    void withdrawBalance_WhenValidRequest_WithdrawsAndCreatesTransaction() {
        String accountId = "accountId";
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50L);
        FetchAccountBalanceResponseDO accountBalance = new FetchAccountBalanceResponseDO(BigDecimal.valueOf(200L));

        when(accountDAO.isAccountExist(accountId)).thenReturn(true);
        when(accountDAO.fetchAccountBalance(accountId)).thenReturn(accountBalance);

        WithdrawBalanceRequest withdrawBalanceRequest = new WithdrawBalanceRequest(accountId, withdrawalAmount);

        withdrawBalanceAccountService.withdrawBalance(withdrawBalanceRequest);

        verify(transactionDAO, times(1)).createTransaction(any());
    }
}
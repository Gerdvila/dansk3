package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.response.FetchAccountTransactionsResponse;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchLatestTransactionsResponseDO;
import com.customer.account.domain.aggregates.response.FetchTransactionResponseDO;
import com.customer.account.domain.primitives.enums.TransactionType;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.TransactionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FetchAccountTransactionServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private TransactionDAO transactionDAO;

    @InjectMocks
    private FetchAccountTransactionService fetchAccountTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAccountTransactions_WhenAccountDoesNotExist_ThrowAccountNotPreviousException() {
        String accountId = "accountId";
        when(accountDAO.isAccountExist(accountId)).thenReturn(false);

        AccountNotPreviousException e = assertThrows(AccountNotPreviousException.class,
                () -> fetchAccountTransactionService.fetchAccountTransactions(accountId));

        assertEquals("Account is not previous.", e.getMessage());
    }

    @Test
    void fetchAccountTransactions_WhenNoTransactions_ReturnsNull() {
        String accountId = "accountId";
        when(accountDAO.isAccountExist(accountId)).thenReturn(true);
        when(transactionDAO.fetchLatestTransactions(new FetchLatestTransactionsRequestDO(accountId, null))).thenReturn(null);

        FetchAccountTransactionsResponse response = fetchAccountTransactionService.fetchAccountTransactions(accountId);

        assertNull(response);
    }

    @Test
    void fetchAccountTransactions_WhenTransactionsExist_ReturnsTransactionList() {
        String accountId = "accountId";
        when(accountDAO.isAccountExist(accountId)).thenReturn(true);

        FetchTransactionResponseDO transaction1 = new FetchTransactionResponseDO("txn1","txn1", TransactionType.DEPOSIT, BigDecimal.valueOf(100L));
        FetchTransactionResponseDO transaction2 = new FetchTransactionResponseDO("txn2","txn2", TransactionType.WITHDRAWAL, BigDecimal.valueOf(50L));
        FetchLatestTransactionsResponseDO transactionsResponseDO = new FetchLatestTransactionsResponseDO(List.of(transaction1, transaction2));

        when(transactionDAO.fetchLatestTransactions(any()))
                .thenReturn(transactionsResponseDO);

        FetchAccountTransactionsResponse response = fetchAccountTransactionService.fetchAccountTransactions(accountId);

        assertEquals(2, response.getTransactions().size());
        assertEquals("txn1", response.getTransactions().get(0).getId());
        assertEquals(TransactionType.DEPOSIT, response.getTransactions().get(0).getTransactionType());
        assertEquals(BigDecimal.valueOf(100L), response.getTransactions().get(0).getAmount());

        assertEquals("txn2", response.getTransactions().get(1).getId());
        assertEquals(TransactionType.WITHDRAWAL, response.getTransactions().get(1).getTransactionType());
        assertEquals(BigDecimal.valueOf(50L), response.getTransactions().get(1).getAmount());
    }
}
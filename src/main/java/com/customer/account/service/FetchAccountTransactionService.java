package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.response.FetchAccountTransactionsResponse;
import com.customer.account.api.model.aggregates.response.TransactionResponse;
import com.customer.account.domain.aggregates.request.FetchLatestTransactionsRequestDO;
import com.customer.account.domain.aggregates.response.FetchLatestTransactionsResponseDO;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.TransactionDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchAccountTransactionService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public FetchAccountTransactionService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    public FetchAccountTransactionsResponse fetchAccountTransactions(String accountId) {
        if (!accountDAO.isAccountExist(accountId)) {
            throw new AccountNotPreviousException("Account is not previous.");
        }
        FetchLatestTransactionsResponseDO fetchTransactionsResponseDO = transactionDAO.fetchLatestTransactions(new FetchLatestTransactionsRequestDO(accountId, null));
        if (fetchTransactionsResponseDO == null) {
            return null;
        }
        return mapAccountTransactionsDOtoResponse(fetchTransactionsResponseDO);
    }

    private FetchAccountTransactionsResponse mapAccountTransactionsDOtoResponse(FetchLatestTransactionsResponseDO fetchLatestTransactionsResponseDO) {
        List<TransactionResponse> transactionResponses =
                fetchLatestTransactionsResponseDO.getTransactionResponses().stream()
                        .map(transaction -> new TransactionResponse(
                                transaction.getId(),
                                transaction.getTransactionType(),
                                transaction.getAmount()
                        )).collect(Collectors.toList());
        return new FetchAccountTransactionsResponse(transactionResponses);
    }
}
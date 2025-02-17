package com.customer.account.service;


import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.request.DepositBalanceRequest;
import com.customer.account.domain.aggregates.request.AddAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.request.CreateTransactionRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.primitives.enums.TransactionType;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.TransactionDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DepositBalanceAccountService {

    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public DepositBalanceAccountService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    @Transactional
    public void addBalance(DepositBalanceRequest depositBalanceRequest) {
        if(!accountDAO.isAccountExist(depositBalanceRequest.getAccountId())) {
            throw new AccountNotPreviousException("Account is not previous.");
        }
        FetchAccountBalanceResponseDO fetchAccountBalanceResponseDO = accountDAO.fetchAccountBalance(depositBalanceRequest.getAccountId());
        accountDAO.addAccountBalance(new AddAccountBalanceRequestDO(depositBalanceRequest.getAccountId(), depositBalanceRequest.getAmount()));
        transactionDAO.createTransaction(mapCreateTransactionRequestDO(depositBalanceRequest));
    }

    private CreateTransactionRequestDO mapCreateTransactionRequestDO(DepositBalanceRequest depositBalanceRequest) {
        return new CreateTransactionRequestDO(
                UUID.randomUUID().toString(),
                depositBalanceRequest.getAccountId(),
                TransactionType.DEPOSIT,
                depositBalanceRequest.getAmount()
        );
    }
}
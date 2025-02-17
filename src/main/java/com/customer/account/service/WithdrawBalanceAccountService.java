package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.exceptions.BalanceValidForWithdrawalException;
import com.customer.account.api.model.aggregates.request.WithdrawBalanceRequest;
import com.customer.account.domain.aggregates.request.CreateTransactionRequestDO;
import com.customer.account.domain.aggregates.request.WithdrawAccountBalanceRequestDO;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.domain.primitives.enums.TransactionType;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.TransactionDAO;
import com.customer.account.util.ValidationUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WithdrawBalanceAccountService {
    private final AccountDAO accountDAO;
    private final TransactionDAO transactionDAO;

    public WithdrawBalanceAccountService(AccountDAO accountDAO, TransactionDAO transactionDAO) {
        this.accountDAO = accountDAO;
        this.transactionDAO = transactionDAO;
    }

    @Transactional
    public void withdrawBalance(WithdrawBalanceRequest withdrawBalanceRequest) {
        if (!accountDAO.isAccountExist(withdrawBalanceRequest.getAccountId())) {
            throw new AccountNotPreviousException("Account is not previous.");
        }
        FetchAccountBalanceResponseDO fetchAccountBalanceResponseDO = accountDAO.fetchAccountBalance(withdrawBalanceRequest.getAccountId());

        if (ValidationUtil.isBalanceValidForWithdrawal(fetchAccountBalanceResponseDO.getBalance(), withdrawBalanceRequest.getAmount())) {
            throw new BalanceValidForWithdrawalException("Balance is too low to withdraw provided amount");
        }

        accountDAO.withdrawAccountBalance(new WithdrawAccountBalanceRequestDO(withdrawBalanceRequest.getAccountId(), withdrawBalanceRequest.getAmount()));
        transactionDAO.createTransaction(mapCreateTransactionRequestDO(withdrawBalanceRequest));
    }

    private CreateTransactionRequestDO mapCreateTransactionRequestDO(WithdrawBalanceRequest withdrawBalanceRequest) {
        return new CreateTransactionRequestDO(
                UUID.randomUUID().toString(),
                withdrawBalanceRequest.getAccountId(),
                TransactionType.DEPOSIT,
                withdrawBalanceRequest.getAmount()
        );
    }
}
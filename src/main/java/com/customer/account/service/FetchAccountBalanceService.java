package com.customer.account.service;

import com.customer.account.api.exceptions.AccountNotPreviousException;
import com.customer.account.api.model.aggregates.response.FetchAccountBalanceResponse;
import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import com.customer.account.repository.AccountDAO;
import org.springframework.stereotype.Service;

@Service
public class FetchAccountBalanceService {

    private final AccountDAO accountDAO;

    public FetchAccountBalanceService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public FetchAccountBalanceResponse fetchAccountBalance(String accountId) {
        FetchAccountBalanceResponseDO fetchAccountBalanceResponseDO = accountDAO.fetchAccountBalance(accountId);
        if (fetchAccountBalanceResponseDO == null) {
            throw new AccountNotPreviousException("Account not found");
        }
        return new FetchAccountBalanceResponse(fetchAccountBalanceResponseDO.getBalance());
    }
}
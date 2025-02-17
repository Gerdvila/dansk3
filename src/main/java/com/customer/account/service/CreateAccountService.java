package com.customer.account.service;

import com.customer.account.api.model.aggregates.request.CreateAccountRequest;
import com.customer.account.api.model.aggregates.response.CreateAccountResponse;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.repository.AccountDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateAccountService {

    private final AccountDAO accountDAO;

    public CreateAccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        String accountId = UUID.randomUUID().toString();
        System.out.println(createAccountRequest.getCustomerId());
        CreateAccountRequestDO createAccountRequestDO = new CreateAccountRequestDO(accountId, createAccountRequest.getCustomerId());
        accountDAO.createAccount(createAccountRequestDO);
        return new CreateAccountResponse(createAccountRequestDO.getAccountId());
    }
}
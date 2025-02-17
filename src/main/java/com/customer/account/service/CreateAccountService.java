package com.customer.account.service;

import com.customer.account.api.exceptions.CustomerNotPreviousException;
import com.customer.account.api.model.aggregates.request.CreateAccountRequest;
import com.customer.account.api.model.aggregates.response.CreateAccountResponse;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.CustomerDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateAccountService {

    private final AccountDAO accountDAO;
    private final CustomerDAO customerDAO;

    public CreateAccountService(AccountDAO accountDAO, CustomerDAO customerDAO) {
        this.accountDAO = accountDAO;
        this.customerDAO = customerDAO;
    }

    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        if (!customerDAO.isCustomerExist(createAccountRequest.getCustomerId())) {
            throw new CustomerNotPreviousException("Customer is not previous.");
        }
        String accountId = UUID.randomUUID().toString();
        CreateAccountRequestDO createAccountRequestDO = new CreateAccountRequestDO(accountId, createAccountRequest.getCustomerId());
        accountDAO.createAccount(createAccountRequestDO);
        return new CreateAccountResponse(createAccountRequestDO.getAccountId());
    }
}
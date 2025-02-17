package com.customer.account.service;

import com.customer.account.repository.AccountDAO;
import org.springframework.stereotype.Service;

@Service
public class FetchAccountService {

    private final AccountDAO accountDAO;

    public FetchAccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}

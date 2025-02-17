package com.customer.account.domain.aggregates.request;

public class CreateAccountRequestDO {
    private final String accountId;
    private final String customerId;


    public CreateAccountRequestDO(String accountId, String customerId) {
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

}
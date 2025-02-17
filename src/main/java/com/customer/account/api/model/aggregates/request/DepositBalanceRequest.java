package com.customer.account.api.model.aggregates.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositBalanceRequest {

    @NotBlank(message = "AccountID cannot be null.")
    private final String accountId;

    @NotNull(message = "An amount has to be specified")
    @Min(message = "Value must be above 0", value = 0)
    private final BigDecimal amount;


    @JsonCreator
    public DepositBalanceRequest(String accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
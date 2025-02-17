package com.customer.account.api.model.aggregates.request;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class CreateAccountRequest {

    @NotBlank(message = "CustomerID cannot be null.")
    private final String customerId;

    @JsonCreator
    public CreateAccountRequest(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
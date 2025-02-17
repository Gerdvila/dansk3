package com.customer.account.api.exceptions;

public class CustomerNotPreviousException extends RuntimeException {
    public CustomerNotPreviousException(String message) {
        super(message);
    }
}

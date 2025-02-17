package com.customer.account.api.exceptions;

public class AccountNotPreviousException extends RuntimeException {
    public AccountNotPreviousException(String message) {
        super(message);
    }
}

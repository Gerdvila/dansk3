package com.customer.account.api.exceptions;

public class BalanceValidForWithdrawalException extends RuntimeException {
    public BalanceValidForWithdrawalException(String message) {
        super(message);
    }
}

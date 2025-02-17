package com.customer.account.util;

import java.math.BigDecimal;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static boolean isBalanceValidForWithdrawal(BigDecimal currentBalance, BigDecimal withdrawalAmount) {
        return withdrawalAmount.compareTo(currentBalance) >= 0;
    }
}
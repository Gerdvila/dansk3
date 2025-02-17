package com.customer.account.repository.mapper;

import com.customer.account.domain.aggregates.response.FetchTransactionResponseDO;
import com.customer.account.domain.primitives.enums.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchTransactionsMapper implements RowMapper<FetchTransactionResponseDO> {

    private static final String ID = "id";
    private static final String ACCOUNT_ID = "account_id";
    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final String TRANSACTION_AMOUNT = "amount";


    @Override
    public FetchTransactionResponseDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FetchTransactionResponseDO(
                rs.getString(ID),
                rs.getString(ACCOUNT_ID),
                TransactionType.valueOf(rs.getString(TRANSACTION_TYPE)),
                rs.getBigDecimal(TRANSACTION_AMOUNT)
        );
    }
}
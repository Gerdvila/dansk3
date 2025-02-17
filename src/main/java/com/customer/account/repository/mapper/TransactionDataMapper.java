package com.customer.account.repository.mapper;

import com.customer.account.domain.aggregates.response.FetchTransactionsResponseDO;
import com.customer.account.domain.primitives.enums.TransactionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDataMapper implements RowMapper<FetchTransactionsResponseDO> {

    private static final String ID = "id";
    private static final String ACCOUNT_ID = "account_id";
    private static final String TRANSACTION_TYPE = "transaction_type";
    private static final String TRANSACTION_AMOUNT = "amount";


    @Override
    public FetchTransactionsResponseDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FetchTransactionsResponseDO(
                rs.getString(ID),
                rs.getString(ACCOUNT_ID),
                rs.getObject(TRANSACTION_TYPE, TransactionType.class),
                rs.getBigDecimal(TRANSACTION_AMOUNT));
    }
}

package com.customer.account.repository.mapper;

import com.customer.account.domain.aggregates.response.FetchAccountBalanceResponseDO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FetchAccountBalanceMapper implements RowMapper<FetchAccountBalanceResponseDO> {

    private static final String BALANCE = "balance";

    @Override
    public FetchAccountBalanceResponseDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FetchAccountBalanceResponseDO(
                rs.getBigDecimal(BALANCE));
    }
}
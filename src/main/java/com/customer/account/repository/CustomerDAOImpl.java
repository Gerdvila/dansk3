package com.customer.account.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    private static final String CUSTOMER_ID = "id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean isCustomerExist(String customerId){
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM customer\n" +
                "WHERE id = :id\n" +
                ");";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue(CUSTOMER_ID, customerId);

        return namedParameterJdbcTemplate.queryForObject(query, params, Boolean.class);
    }
}

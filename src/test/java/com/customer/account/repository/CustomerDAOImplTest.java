package com.customer.account.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerDAOImplTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectMocks
    private CustomerDAOImpl customerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isCustomerExist_WhenCustomerExists_ReturnsTrue() {
        String customerId = "existingCustomer";
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM customer\n" +
                "WHERE id = :id\n" +
                ");";

        when(namedParameterJdbcTemplate.queryForObject(eq(query), any(MapSqlParameterSource.class), eq(Boolean.class)))
                .thenReturn(true);

        boolean exists = customerDAO.isCustomerExist(customerId);

        assertTrue(exists);
    }

    @Test
    void isCustomerExist_WhenCustomerDoesNotExist_ReturnsFalse() {
        String customerId = "nonExistentCustomer";
        String query = "SELECT EXISTS(\n" +
                "SELECT 1\n" +
                "FROM customer\n" +
                "WHERE id = :id\n" +
                ");";

        when(namedParameterJdbcTemplate.queryForObject(eq(query), any(MapSqlParameterSource.class), eq(Boolean.class)))
                .thenReturn(false);

        boolean exists = customerDAO.isCustomerExist(customerId);

        assertFalse(exists);
    }
}
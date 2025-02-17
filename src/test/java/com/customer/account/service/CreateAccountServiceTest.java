package com.customer.account.service;

import com.customer.account.api.exceptions.CustomerNotPreviousException;
import com.customer.account.api.model.aggregates.request.CreateAccountRequest;
import com.customer.account.api.model.aggregates.response.CreateAccountResponse;
import com.customer.account.domain.aggregates.request.CreateAccountRequestDO;
import com.customer.account.repository.AccountDAO;
import com.customer.account.repository.CustomerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateAccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private CustomerDAO customerDAO;

    @InjectMocks
    private CreateAccountService createAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_WhenCustomerDoesNotExist_ThrowsCustomerNotPreviousException() {
        String customerId = "nonExistentCustomer";
        CreateAccountRequest request = new CreateAccountRequest(customerId);

        when(customerDAO.isCustomerExist(customerId)).thenReturn(false);

        CustomerNotPreviousException e = assertThrows(CustomerNotPreviousException.class,
                () -> createAccountService.createAccount(request));

        assertEquals("Customer is not previous.", e.getMessage());
    }

    @Test
    void createAccount_WhenValidRequest_CreatesAccountAndReturnsResponse() {
        String customerId = "validCustomer";
        CreateAccountRequest request = new CreateAccountRequest(customerId);

        when(customerDAO.isCustomerExist(customerId)).thenReturn(true);

        CreateAccountResponse response = createAccountService.createAccount(request);

        assertNotNull(response.getAccountId());
        verify(accountDAO, times(1)).createAccount(any(CreateAccountRequestDO.class));
    }
}
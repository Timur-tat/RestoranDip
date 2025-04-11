package com.diplom.restoran.service;

import com.diplom.restoran.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
@Mock
CustomerRepository customerRepository;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void saveCustomer() {
    }

    @Test
    void saveCustomerOrderToCustomer() {
    }

    @Test
    void removeCustomerOrderFromCustomer() {
    }

    @Test
    void setAllPriceTotalAmount() {
    }
}
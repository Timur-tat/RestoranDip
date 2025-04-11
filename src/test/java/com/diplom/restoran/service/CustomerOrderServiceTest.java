package com.diplom.restoran.service;

import com.diplom.restoran.dto.CustomerOrderDTO;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.repository.*;
import com.diplom.restoran.security.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceTest {
@Mock
private UserRepository userRepository;
    @Mock
    private  CustomerOrderRepository customerOrderRepository;
    @Mock
    private  CustomerRepository customerRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private ChefRepository chefRepository;
    @Mock
    private WaiterRepository waiterRepository;
    @InjectMocks
    private CustomerOrderService customerOrderService;
    CustomerOrder customerOrder1;
    CustomerOrder customerOrder2;
    CustomerOrder customerOrder3;
    CustomerOrderDTO customerDTOOrder1;
    CustomerOrderDTO customerDTOOrder2;
    CustomerOrderDTO customerDTOOrder3;
    List<CustomerOrderDTO> customerOrderDTOList=new ArrayList<>();
    List<CustomerOrder> customerOrderList=new ArrayList<>();
    @BeforeEach
    void setUp() {
        customerOrder1 = new CustomerOrder();
        customerOrder2 = new CustomerOrder();
        customerOrder3 = new CustomerOrder();
        customerDTOOrder1 = new CustomerOrderDTO();
        customerDTOOrder2 = new CustomerOrderDTO();
        customerDTOOrder3 = new CustomerOrderDTO();
        customerOrderList.add(customerOrder1);
        customerOrderList.add(customerOrder2);
        customerOrderList.add(customerOrder3);
        customerOrderDTOList.add(customerDTOOrder1);
        customerOrderDTOList.add(customerDTOOrder2);
        customerOrderDTOList.add(customerDTOOrder3);
    }

    @AfterEach
    void tearDown() {
        customerOrderList.removeAll(customerOrderList);
        customerOrder1=null;
        customerOrder2=null;
        customerOrder3=null;
        customerOrderDTOList.removeAll(customerOrderDTOList);
        customerDTOOrder1=null;
        customerDTOOrder2=null;
        customerDTOOrder3=null;
    }

    @Test
    void getAllCustomerOrders() {
        when(customerOrderRepository.findAll()).thenReturn(customerOrderList);
        assertNotEquals(customerOrderList, customerOrderService.getAllCustomerOrders());
        assertEquals(customerOrderList.size(), customerOrderService.getAllCustomerOrders().size());
        assertEquals(customerOrderDTOList, customerOrderService.getAllCustomerOrders().size());
    }

    @Test
    void getCustomerOrderByIdDTO() {
    }

    @Test
    void getCustomerOrderById() {
    }

    @Test
    void getOrdersWithNullWaiter() {
    }

    @Test
    void getOrdersWithNullChef() {
    }

    @Test
    void getOrdersWithNullWaiterAndChef() {
    }

    @Test
    void getDishesByCustomerOrderId() {
    }

    @Test
    void addChefToCustomerOrder() {
    }

    @Test
    void addWaiterToCustomerOrder() {
    }

    @Test
    void customerOrderWithNullWaiter() {
    }

    @Test
    void customerOrderWithNullChef() {
    }

    @Test
    void setAllPriceTotalAmount() {
    }

    @Test
    void saveCustomerOrder() {
    }

    @Test
    void saveCustomerOrder1() {
    }

    @Test
    void addDishToOrder() {
    }

    @Test
    void testSaveCustomerOrder() {
    }

    @Test
    void paidIsTrue() {
    }

    @Test
    void deleteCustomerOrder() {
    }

    @Test
    void getCurrentCustomer() {
    }
}
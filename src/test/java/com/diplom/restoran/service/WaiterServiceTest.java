package com.diplom.restoran.service;

import com.diplom.restoran.dto.WaiterDTO;
import com.diplom.restoran.entity.Waiter;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.repository.WaiterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaiterServiceTest {
    @Mock
    private  WaiterRepository waiterRepository;
    @Mock
    private CustomerOrderRepository customerOrderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private WaiterService waiterService;
    WaiterDTO waiterDTO1;
    WaiterDTO waiterDTO2;
    WaiterDTO waiterDTO3;
    Waiter waiter1;
    Waiter waiter2;
    Waiter waiter3;
    List<Waiter> waiterList=new ArrayList<>();
    List<WaiterDTO> waiterDTOList=new ArrayList<>();

    @BeforeEach
    void setUp() {
        waiter1=new Waiter();
        waiter2=new Waiter();
        waiter3=new Waiter();
        waiterDTO1=new WaiterDTO();
        waiterDTO2=new WaiterDTO();
        waiterDTO3=new WaiterDTO();

        waiterList.add(waiter1);
        waiterList.add(waiter2);
        waiterList.add(waiter3);
        waiterDTOList.add(waiterDTO1);
        waiterDTOList.add(waiterDTO2);
        waiterDTOList.add(waiterDTO3);
    }

    @AfterEach
    void tearDown() {
        waiterList.removeAll(waiterList);
        waiter1=null;
        waiter2=null;
        waiter3=null;
        waiterDTOList.removeAll(waiterDTOList);
        waiterDTO1=null;
        waiterDTO2=null;
        waiterDTO3=null;

    }

    @Test
    void getAllWaiters() {
        when(waiterRepository.findAll()).thenReturn(waiterList);
        assertNotEquals(waiterList, waiterService.getAllWaiters());
        assertEquals(waiterList.size(), waiterService.getAllWaiters().size());
        assertEquals(waiterDTOList, waiterService.getAllWaiters().stream().map(waiter -> new WaiterDTO(waiter.getId(),waiter.getName(),null)).collect(Collectors.toList()));

    }

    @Test
    void getWaiterByIdDTO() {
    }

    @Test
    void getWaiterById() {
        when(waiterRepository.findById(1L)).thenReturn(Optional.of(waiter1));
        waiterService.getWaiterById(1L);
    }

    @Test
    void saveWaiter() {
    }

    @Test
    void testSaveWaiter() {
    }

    @Test
    void saveCustomerOrderToWaiter() {
    }

    @Test
    void removeCustomerOrderFromWaiter() {
    }
}
package com.diplom.restoran.service;

import com.diplom.restoran.dto.ChefDTO;
import com.diplom.restoran.entity.Chef;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.ChefRepository;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChefServiceTest {
    @Mock
    private  ChefRepository chefRepository;
    @Mock
    private  CustomerOrderRepository customerOrderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private ChefService chefService;
private Chef chef1=new Chef();
private Chef chef2=new Chef();
private Chef chef3=new Chef();

ChefDTO chefDTO1=new ChefDTO();
ChefDTO chefDTO2=new ChefDTO();
ChefDTO chefDTO3=new ChefDTO();
List<ChefDTO> chefDTOList=new ArrayList<>();
List<Chef> chefList=new ArrayList<>();
private CustomerOrder customerOrder1=new CustomerOrder();
    @BeforeEach
    void setUp() {

        chef1 = new Chef(1L,"Test Chef1",new ArrayList<>());
        chef1.addToOrders(new CustomerOrder());
        chef2 = new Chef(2L,"Test Chef2",new ArrayList<>());
        chef2.addToOrders(new CustomerOrder());
        chef3 = new Chef(3L,"Test Chef3",new ArrayList<>());
        chef3.addToOrders(new CustomerOrder());
        chefDTO1 = new ChefDTO(chef1.getId(),chef1.getName(), chef1.getOrders().stream().map(x->x.getId()).collect(Collectors.toList()));
        chefDTO2 = new ChefDTO(chef2.getId(),chef2.getName(), chef2.getOrders().stream().map(x->x.getId()).collect(Collectors.toList()));
        chefDTO3 = new ChefDTO(chef3.getId(),chef3.getName(), chef3.getOrders().stream().map(x->x.getId()).collect(Collectors.toList()));
        chefList.add(chef1);
        chefList.add(chef2);
        chefList.add(chef3);
        chefDTOList.add(chefDTO1);
        chefDTOList.add(chefDTO2);
        chefDTOList.add(chefDTO3);
        customerOrder1 = new CustomerOrder(1L,null,null,null,null,new ArrayList<>(),0.0,false);
    }

    @AfterEach
    void tearDown() {
        chefList.removeAll(chefList);
        chef1=null;
        chef2=null;
        chef3=null;
        chefDTOList.removeAll(chefDTOList);
        chefDTO1=null;
        chefDTO2=null;
        chefDTO3=null;
    }

    @Test
    void getAllChefs() {
        when(chefRepository.findAll()).thenReturn(chefList);
        assertNotEquals(chefList,chefService.getAllChefs());
        assertEquals(chefList.size(),chefService.getAllChefs().size());
        assertEquals(chefDTOList,chefService.getAllChefs().stream().map(chef -> new ChefDTO(chef.getId(),chef.getName(), null)).collect(Collectors.toList()));
    }

    @Test
    void saveChef() {
    }

    @Test
    void getChefById() {
        when((chefRepository.findById(1L))).thenReturn(Optional.of(chef1));
        assertEquals(chefDTO1,chefService.getChefById(1L));
    }

    @Test
    void saveCustomerOrderToChef() {
        when(chefRepository.findById(1L)).thenReturn(Optional.of(chef1));
        when(customerOrderRepository.findById(1L)).thenReturn(Optional.of(customerOrder1));
        when(chefRepository.save(chef1)).thenReturn(chef1);
        assertEquals(chefService.saveCustomerOrderToChef(1L,1L),chef1);
        verify(chefRepository).findById(1L);
        assertThrows(NotFoundException.class,()->chefService.saveCustomerOrderToChef(1L,2L));

    }
    @Test
    void saveCustomerOrderToChefExeption() {
        when(chefRepository.findById(1L)).thenReturn(Optional.empty());
        // when(customerOrderRepository.findById(1L)).thenReturn(Optional.of(customerOrder1));
       // when(chefRepository.save(chef1)).thenReturn(chef1);
       // assertEquals(chefService.saveCustomerOrderToChef(1L,1L),chef1);
        //verify(chefRepository).findById(1L);
        assertThrows(NotFoundException.class,()->chefService.saveCustomerOrderToChef(1L,1L));

    }
}
package com.diplom.restoran.service;

import com.diplom.restoran.dto.DishDTO;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.repository.DishRepository;
import com.diplom.restoran.service.DishService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @InjectMocks
    private DishService dishService;

    private final Dish testDish = new Dish(1L, "Test Dish", 10.0, "decription", false, new ArrayList<>());
    private final Dish testDish2 = new Dish(1L, "Test Dish", 10.0, "decription", false, new ArrayList<>());
    private final Dish testDish3 = new Dish(1L, "Test Dish", 10.0, "decription", false, new ArrayList<>());
List<Dish> dishList = new ArrayList<>();
@BeforeEach
void setUp() {
    // Настройка аутентификации
    Authentication authentication = new UsernamePasswordAuthenticationToken(
            "testUser",
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    dishList.add(testDish);
    dishList.add(testDish2);
    dishList.add(testDish3);
}
@AfterEach
void tearDown() {
    dishList.clear();

}
    @Test
    void getAllDishes_ShouldReturnListOfDishes() {
        // Arrange
        when(dishRepository.findAll()).thenReturn(dishList);

        // Act
        List<DishDTO> result = dishService.getAllDishes();

        // Assert
        assertEquals(3, result.size());
        DishDTO dto = result.get(0);
        assertEquals(testDish.getId(), dto.getId());
        assertEquals(testDish.getName(), dto.getName());
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    @WithMockUser(username = "testUser")
    void getDishById_WhenDishExists_ShouldReturnDishDTO() throws Exception {
        // Arrange
        when(dishRepository.findById(1L)).thenReturn(Optional.of(testDish));

        // Act
        DishDTO result = dishService.getDishById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testDish.getId(), result.getId());
        assertEquals(testDish.getName(), result.getName());
        verify(dishRepository, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(username = "testUser")
    void getDishById_WhenDishNotExists_ShouldThrowException() {
        // Arrange
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            dishService.getDishById(99L);
        });
        verify(dishRepository, times(1)).findById(99L);
    }

    @Test
    @WithMockUser(username = "testUser")
    void getDishById_ShouldLogAppropriateMessages() {
        // Arrange
        when(dishRepository.findById(1L)).thenReturn(Optional.of(testDish));

        // Act
        dishService.getDishById(1L);

        // Verify logging using Mockito (assuming you have proper logging setup)
        // Можно добавить проверку логов через Mockito или специальный аппендер
    }

    @Test
    void saveDish_ShouldSaveAndReturnDto() {
        // Arrange
        DishDTO inputDto = new DishDTO(null, "Pizza", "Delicious", 12.99, true, Collections.emptyList());
        Dish savedDish = new Dish(1L, "Pizza",12.99 , "Delicious", true, new ArrayList<>());

        when(dishRepository.save(any(Dish.class))).thenReturn(savedDish);

        // Act
        DishDTO result = dishService.saveDish(inputDto);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Pizza", result.getName());
        verify(dishRepository).save(argThat(dish ->
                dish.getName().equals("Pizza") &&
                        dish.getPrice() == 12.99
        ));
    }

    @Test
    void saveDish_WithNonExistingCustomerOrders_ShouldThrowException() {
        // Arrange
        DishDTO inputDto = new DishDTO(null, "Pizza", "Desc", 10.0, true, List.of(999L));
        when(customerOrderRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> dishService.saveDish(inputDto));
        verify(customerOrderRepository).findById(999L);
    }

//    @Test
//    void getDishesUnderPrice_ShouldReturnFilteredDishes() {
//        // Arrange
//        List<Dish> mockDishes = List.of(
//                new Dish(1L, "Soup", "Hot", 5
}
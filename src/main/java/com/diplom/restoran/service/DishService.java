package com.diplom.restoran.service;

import com.diplom.restoran.dto.DishDTO;
import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.repository.DishRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class DishService extends CurrentUserSecurity {
    private static final Logger logger = LoggerFactory.getLogger(DishService.class);
    private final DishRepository dishRepository;
    private final CustomerRepository customerRepository;
private final CustomerOrderRepository customerOrderRepository;
    public DishService(DishRepository dishRepository,
                       CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository ) {
        this.dishRepository = dishRepository;
        this.customerRepository = customerRepository;
        this.customerOrderRepository=customerOrderRepository;

    }
    public List<DishDTO> getAllDishes() {
        return dishRepository.findAll().stream()
                .map(dish -> new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice(), dish.getAvailable(),new ArrayList<Long>())).collect(Collectors.toList());

    } //рабочий метод



//    public DishDTO getDishById(Long id) throws NotFoundException {
//        Optional<Dish> optionalDish = dishRepository.findById(id);
//        if (optionalDish.isEmpty()){
//            throw new NotFoundException("Dish not found");
//        }
//        Dish dish = optionalDish.get();
//        return new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice(), dish.getAvailable(), new ArrayList<Long>());// не ставится .stream().map(x->x.getId()).collect(Collectors.toList()))
//    } //рабочий
    public DishDTO getDishById(Long id) throws NotFoundException {
        log.info("Запрос на получение блюда по ID: {}, {}", id, getCurrentUsername());
        Optional<Dish> optionalDish = dishRepository.findById(id);

        if (optionalDish.isEmpty()) {
            log.error("Блюдо с ID {} не найдено, пользователя {}" , id, getCurrentUsername());
            throw new NotFoundException("Dish not found with id: " + id);
        }

        Dish dish = optionalDish.get();
        log.debug("Найдено блюдо: {}", dish);


        DishDTO dishDTO = new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice(), dish.getAvailable(), new ArrayList<>());
        return dishDTO;
    }
    @Transactional
public DishDTO saveDish(DishDTO dishDTO) {
    Dish dish = new Dish();
    //dish.setId(dishDTO.getId()); // Удаляем это
    dish.setName(dishDTO.getName());
    dish.setDescription(dishDTO.getDescription());
    dish.setPrice(dishDTO.getPrice());
    dish.setAvailable(dishDTO.getAvailable());

    // Получаем список Customer (даже если он пустой в DTO)
    List<CustomerOrder> customerOrders = new ArrayList<>();
    if (dishDTO.getCustomerOrders() != null && !dishDTO.getCustomerOrders().isEmpty()) {
        try {
            customerOrders = dishDTO.getCustomerOrders().stream()
                    .map(customerOrdersId -> customerOrderRepository.findById(customerOrdersId)
                            .orElseThrow(() -> new NotFoundException("Customer not found with id: " + customerOrdersId)))
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            logger.error("Error finding customer", e.getMessage());
            throw new NotFoundException("Error finding customer ");
        }
    }
    dish.setCustomerOrders(customerOrders);

    // Сохраняем блюдо
    Dish savedDish = dishRepository.save(dish);

    // Преобразуем Dish в DishDTO (реализуйте метод convertToDto)
    return convertToDto(savedDish);
}
    private DishDTO convertToDto(Dish dish) {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setAvailable(dish.getAvailable());
        // dishDTO.setCustomers(...) //Преобразуйте customers, если это необходимо
        return dishDTO;
    }
    public List<DishDTO> getDishesUnderPrice(Double maxPrice) {
        List<Dish> dishes = dishRepository.findByPriceLessThan(maxPrice);

        // Convert Dish objects to DishDTO objects
        return dishes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public void deleteDish(Long id) {
        // 1. Проверяем, существует ли блюдо с указанным ID
        if (!dishRepository.existsById(id)) {
            throw new NotFoundException("Dish not found with id: " + id);
        }

        // 2. Удаляем блюдо по ID
        dishRepository.deleteById(id);
    }

}

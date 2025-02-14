package com.diplom.restoran.controller;

import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.entity.Waiter;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.repository.DishRepository;
import com.diplom.restoran.repository.WaiterRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Управление заказами в ресторане")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private DishRepository dishRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder order) {
        // 1️⃣ Проверяем существование клиента
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseGet(() -> customerRepository.save(order.getCustomer()));

        // 2️⃣ Проверяем существование официанта
        Waiter waiter = waiterRepository.findById(order.getWaiter().getId())
                .orElseGet(() -> waiterRepository.save(order.getWaiter()));

        // 3️⃣ Проверяем существование каждого блюда
        List<Dish> dishes = order.getDishes().stream()
                .map(dish -> dishRepository.findById(dish.getId())
                        .orElseGet(() -> dishRepository.save(dish)))
                .toList();

        // 4️⃣ Создаём заказ с обновлёнными ссылками
        CustomerOrder newOrder = new CustomerOrder(
                null, order.getStatus(), customer, waiter, dishes,
                order.getTotalAmount(), order.getPaid()
        );

        // 5️⃣ Сохраняем заказ
        CustomerOrder savedOrder = orderRepository.save(newOrder);

        return ResponseEntity.ok(savedOrder);
    }
}
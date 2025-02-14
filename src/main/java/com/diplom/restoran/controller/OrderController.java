package com.diplom.restoran.controller;


import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.exeption.OrderNotFoundException;
import com.diplom.restoran.repository.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер управления заказами.
 * <p>
 * Позволяет создавать, обновлять и получать заказы.
 */

@RestController
@RequestMapping("/orders")
@Validated
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Управление заказами")
public class OrderController {
    private CustomerOrderRepository customerOrderRepository;
    private WaiterRepository waiterRepository;
    private CustomerRepository customerRepository;

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    /**
     * Создает новый заказ.
     *
     * @param customerOrder Новый заказ.
     * @return Созданный заказ.
     */
//    @Transactional
//    @PostMapping
//    @Operation(
//            summary = "Создать заказ",
//            description = "Создает новый заказ и возвращает его",
//            responses = {
//                    @ApiResponse(responseCode = "201", description = "Заказ успешно создан", content = @Content(schema = @Schema(implementation = CustomerOrder.class))),
//                    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
//            }
//    )
//    public ResponseEntity<CustomerOrder> createOrder(@Valid @RequestBody CustomerOrder customerOrder) {
////        if (customerOrder.getCustomer()!= null) {
////            customerRepository.save(customerOrder.getCustomer());
////        }
////        if (customerOrder.getWaiter()!= null) {
////            waiterRepository.save(customerOrder.getWaiter());
////        }
//
//
//// Сохраните заказ
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(customerOrder));
//    }
//
//    /**
//     * Обновляет существующий заказ.
//     *
//     * @param id ID заказа.
//     * @param orderDetails Данные для обновления.
//     * @return Обновленный заказ.
//     */
//    @PutMapping("/{id}")
//    @Operation(
//            summary = "Обновить заказ",
//            description = "Обновляет данные заказа по ID",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "Заказ успешно обновлен", content = @Content(schema = @Schema(implementation = CustomerOrder.class))),
//                    @ApiResponse(responseCode = "404", description = "Заказ не найден"),
//                    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
//            }
//    )
//    public ResponseEntity<CustomerOrder> updateOrder(
//            @Parameter(description = "ID заказа", example = "1") @PathVariable Long id,
//            @Valid @RequestBody CustomerOrder orderDetails
//    ) throws OrderNotFoundException {
//        CustomerOrder order = orderRepository.findById(id)
//                .orElseThrow(() -> new OrderNotFoundException("order not found"+id));
//
//        order.setStatus(orderDetails.getStatus());
//        order.setTotalAmount(orderDetails.getTotalAmount());
//        order.setPaid(orderDetails.getPaid());
//        return ResponseEntity.ok(orderRepository.save(order));
//    }
//
//    /**
//     * Получает список всех заказов.
//     *
//     * @return Список заказов.
//     */
//    @GetMapping
//    @Operation(summary = "Получить все заказы", description = "Возвращает список всех заказов")
//    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
//        return ResponseEntity.ok(orderRepository.findAll());
//    }
}
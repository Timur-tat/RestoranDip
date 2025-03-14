package com.diplom.restoran.controller;

import com.diplom.restoran.dto.CustomerOrderDTO;
import com.diplom.restoran.entity.*;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.*;
import com.diplom.restoran.service.CustomerOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerOrders")
@Tag(name = "CustomerOrders", description = "Управление заказами в ресторане")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class CustomerOrderController {
@Autowired
private CustomerOrderRepository customerOrderRepository;
@Autowired
    CustomerOrderService customerOrderService;
    @Autowired
    private CustomerOrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WaiterRepository waiterRepository;
    @Autowired
    private DishRepository dishRepository;
@Autowired
private ChefRepository chefRepository;

    @PostMapping
    @Operation(summary = "Создать новый CustomerOrder.(НЕ ИСПОЛЬЗОВАТЬ)", description = "createCustomerOrder")
    @Transactional
    public ResponseEntity<CustomerOrder> createCustomerOrder(@RequestBody CustomerOrder order) {
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

//        // 4️⃣ Создаём заказ с обновлёнными ссылками
//        CustomerOrder newOrder = new CustomerOrder(
//                null, chef, order.getStatus(), customer, waiter, dishes,
//                order.getTotalAmount(), order.getPaid()
//        );
//
//        // 5️⃣ Сохраняем заказ
//        CustomerOrder savedOrder = orderRepository.save(newOrder);
//
//        return ResponseEntity.ok(savedOrder);
        return null;
    }
    @PostMapping("/DTO/1")
    @Operation(summary = "Создать новый CustomerOrder.", description = "saveCustomerOrder1")
    public CustomerOrder saveCustomerOrder1(@RequestBody CustomerOrderDTO customerOrderDTO) {
return customerOrderService.saveCustomerOrder1(customerOrderDTO);

    }
    @PostMapping("/DTO")
    @Operation(summary = "Создать новый CustomerOrder.(НЕ ИСПОЛЬЗОВАТЬ)", description = "saveCustomerOrder")
    public CustomerOrder saveCustomerOrder(@RequestBody CustomerOrderDTO customerOrderDTO) {
        // 1️⃣ Проверяем существование клиента
        Customer customer = customerRepository.findById(customerOrderDTO.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        // 3️⃣ Проверяем существование каждого блюда
        List<Dish> dishes = customerOrderDTO.getDishIds().stream()
                .map(dish -> dishRepository.findById(dish)
                        .orElseThrow(() -> new NotFoundException("Dish not found")))
                .toList();

        // 4️⃣ Создаём заказ с ссылками и данными из DTO
        CustomerOrder newOrder = new CustomerOrder(
                null, // id
                null, // chef - У вас тут null, так и оставляем, если это дефолтное значение
                customerOrderDTO.getStatus(),
                customer,
                null, // waiter - У вас тут null, так и оставляем
                dishes,
                customerOrderDTO.getTotalAmount() != null ? customerOrderDTO.getTotalAmount() : 0.0,  // totalAmount из DTO, или 0.0 если в DTO null
                customerOrderDTO.getPaid() != null ? customerOrderDTO.getPaid() : false  // paid из DTO, или false если в DTO null
        );


        // 5️⃣ Сохраняем заказ
        CustomerOrder savedOrder = orderRepository.save(newOrder);

        return savedOrder;
    }

    @GetMapping
    @Operation(summary = "Список всех CustomerOrder.", description = "getAllCustomerOrders")
    public List<CustomerOrder> getAllCustomerOrders() {
        return customerOrderService.getAllCustomerOrders();
    }
    @GetMapping("/addWaiterToCustomerOrder{customerOrderId}/addWaiter/{waiterId}")
    @Operation(summary = "Добавление Waiter в CustomerOrder.", description = "addWaiter")
    public  CustomerOrder addWaiter(@PathVariable Long customerOrderId, @PathVariable Long waiterId) {
        CustomerOrder customerOrder= customerOrderService.addWaiterToCustomerOrder(customerOrderId, waiterId);
    return customerOrder;
    } @GetMapping("/addChefToCustomerOrder{customerOrderId}/addWaiter/{chefId}")
    @Operation(summary = "Добавление Chef в CustomerOrder.", description = "addChef")
    public  CustomerOrder addChef(@PathVariable Long customerOrderId, @PathVariable Long chefId ) {
        CustomerOrder customerOrder= customerOrderService.addChefToCustomerOrder(customerOrderId, chefId);
    return customerOrder;
    }
@GetMapping("/customerOrderWithNullChef")
@Operation(summary = "Список CustomerOrder с Chef Null.", description = "customerOrderWithNullChef")
    public List<CustomerOrder> customerOrderWithNullChef() {
        List<CustomerOrder> customerOrders = customerOrderService.customerOrderWithNullChef();
        return customerOrders;
}
@GetMapping("/customerOrderWithNullWaiter")
@Operation(summary = "Список CustomerOrder с Waiter Null.", description = "customerOrderWithNullWaiter")
    public List<CustomerOrder> customerOrderWithNullWaiter(){
        List<CustomerOrder> customerOrders= customerOrderService.customerOrderWithNullWaiter();
        return customerOrders;
}
@GetMapping("/totalAmountCustomerOrder")
@Operation(summary = "Подсчет общей суммы заказа по его id=", description = "setAllPriceTotalAmount")
    public Double setAllPriceTotalAmount(Long customerOrderId){
       return customerOrderService.setAllPriceTotalAmount(customerOrderId);


}
@GetMapping("/paidIsTrueId/{customerOrderId}")
@Operation(summary = "Подтверждение оплаты заказа по его id.", description = "paidIsTrueId")
    public CustomerOrder paidIsTrueId(Long customerOrderId){
        return customerOrderService.paidIsTrue(customerOrderId);
}
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Удалить CustomerOrder по id.", description = "deleteCustomerOrder")
//    public void deleteCustomerOrder(@PathVariable Long id) {
//        customerOrderService.deleteCustomerOrder(id);
//    }
}
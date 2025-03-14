package com.diplom.restoran.controller;

import com.diplom.restoran.dto.WaiterDTO;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.entity.Waiter;
import com.diplom.restoran.service.CustomerOrderService;
import com.diplom.restoran.service.WaiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiter")
@Tag(name = "Waiter", description = "Управление официантами")
@PreAuthorize("hasRole('WAITER') or hasRole('ADMIN')")
public class WaiterController {
    private final WaiterService waiterService;

    private final CustomerOrderService customerOrderService;

    public WaiterController(WaiterService waiterService, CustomerOrderService customerOrderService) {this.waiterService = waiterService;
        this.customerOrderService = customerOrderService;
    }
    @Operation(summary = "Получить список всех Waiter", description = "Возвращает список всех официантов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список официантов успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WaiterDTO.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера",
                    content = @Content)
    })
    @GetMapping
    public List<WaiterDTO> getWaiters() {return waiterService.getAllWaiters();}
    @Operation(summary = "Добавить нового Waiter", description = "Добавляет нового официанта в систему.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Официант успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content)
    })
    @PostMapping
    public void saveWaiter(@RequestBody WaiterDTO waiterDTO) {
        waiterService.saveWaiter(waiterDTO);
    }
//    @GetMapping
//    public List<WaiterDTO> getCustomers() {return waiterService.getAllWaiters();}
//    @GetMapping("/waiter/{orderId}/{customerOrderId}")
//    public Waiter saveCustomerOrderToCustomer(@PathVariable Long orderId, @PathVariable Long customerOrderId) {
//        return waiterService.saveCustomerOrderToWaiter(orderId, customerOrderId );
//    }
@DeleteMapping("/custmerorder/{waiterId}/{customerOrderIds}")
public Waiter removeCustomerOrderFromWaiter(@PathVariable Long waiterId, @PathVariable Long customerOrderIds) {
    return waiterService.removeCustomerOrderFromWaiter(waiterId,customerOrderIds );
}
    @GetMapping("/custmerorder/{waiterId}/{customerOrderIds}")
    public Waiter   saveCustomerOrderToWaiter(@PathVariable Long waiterId, @PathVariable Long customerOrderIds) {
        return waiterService.saveCustomerOrderToWaiter(waiterId, customerOrderIds );
    }
@GetMapping("/addToCustomerOrder/{waiterId}/{customerOrderId}")
    public Waiter addToCustomerOrder(@PathVariable Long waiterId, @PathVariable Long customerOrderId) {
        Waiter waiter =waiterService.getWaiterById(waiterId);
    CustomerOrder customerOrder= customerOrderService.getCustomerOrderById(customerOrderId);
   customerOrder.setWaiter(waiter);
   waiter.addToOrders(customerOrder);
   return waiterService.saveWaiter(waiter);


}
}

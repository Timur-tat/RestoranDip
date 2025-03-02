package com.diplom.restoran.controller;

import com.diplom.restoran.dto.CustomerDTO;
import com.diplom.restoran.dto.CustomerOrderDTO;
import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.security.models.User;
import com.diplom.restoran.security.repository.UserRepository;
import com.diplom.restoran.service.CustomerOrderService;
import com.diplom.restoran.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.GenerationType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Управление клиентами")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class CustomerController {
    private final CustomerService customerService;
    private CustomerRepository customerRepository;
    private final CustomerOrderService customerOrderService;
    private  UserRepository userRepository;

    public CustomerController(CustomerService customerService, CustomerOrderService customerOrderService, UserRepository userRepository,CustomerRepository customerRepository) {this.customerService = customerService;
        this.customerOrderService = customerOrderService;
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
    }
    @GetMapping
    @Operation(summary = "Список всех Customer.", description = "getCustomers")
    public List<CustomerDTO> getCustomers() {return customerService.getAllCustomers();}

    @PostMapping
    @Operation(summary = "Создание нового Customer", description = "saveCustomer")
    public void saveCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.saveCustomer(customerDTO);
    }

    @DeleteMapping("/custmerorder/{customerId}/{customerOrderId}")
    @Operation(summary = "Удалить CustomerOrder из Customer по id", description = "removeCustomerOrderFromCustomer")
    public Customer removeCustomerOrderFromCustomer(@PathVariable Long customerId, @PathVariable Long customerOrderId) {
        return customerService.removeCustomerOrderFromCustomer(customerId, customerOrderId );
    }
    @GetMapping("/custmerorder/{orderId}/{customerOrderId}")
    @Operation(summary = "Добавить CustomerOrder по id в Customer ", description = "saveCustomerOrderToCustomer")
    public Customer saveCustomerOrderToCustomer(@PathVariable Long orderId, @PathVariable Long customerOrderId) {
        return customerService.saveCustomerOrderToCustomer(orderId, customerOrderId );
    }
    @GetMapping("/getTotalAmount/{customerOrderId}")
    @Operation(summary = "Подсчет TotalAmount для Customer по id", description = "setAllPriceTotalAmount")
    public Double setAllPriceTotalAmount(@PathVariable Long customerOrderId) {
        return customerService.setAllPriceTotalAmount(customerOrderId);
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь не аутентифицирован");
        }

        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Связанный Customer не найден"));

        return ResponseEntity.ok(customer);
    }
}


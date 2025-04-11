package com.diplom.restoran.controller;

import com.diplom.restoran.dto.ChefDTO;
import com.diplom.restoran.entity.Chef;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.ChefRepository;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.service.ChefService;
import com.diplom.restoran.service.CurrentUserSecurity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/chef")
@Tag(name = "Chef", description = "Управление поварами")
@PreAuthorize(" hasRole('CHEF') or hasRole('ADMIN')")

public class ChefController extends CurrentUserSecurity {
    private final ChefService chefService;
    private final CustomerOrderRepository customerOrderRepository;

    public ChefController(ChefService chefService, CustomerOrderRepository customerOrderRepository) {this.chefService = chefService;
        this.customerOrderRepository = customerOrderRepository;
    }
    @PostMapping
    public void saveChef(@RequestBody ChefDTO chefDTO)  {
        chefService.saveChef(chefDTO);
    }
@GetMapping("/{customerOrderId}/{chefId}")
public Chef saveCustomerOrderToChef(@RequestParam Long customerOrderId, @PathVariable Long chefId) {
        return chefService.saveCustomerOrderToChef(customerOrderId, chefId);
}


@GetMapping("/{id}")
    public ChefDTO findChefById(@PathVariable Long id) throws NotFoundException {
    log.info("Запрос на получение шефа по ID: {}, {}", id, getCurrentUsername());
        return chefService.getChefById(id);
}
@GetMapping
    public List<ChefDTO> findAllChefs() {return chefService.getAllChefs();}
    @GetMapping("/chefsIsNull")
    public List<CustomerOrder> getOrdersWithNullChef(@RequestParam Long chefId) throws NotFoundException {
        log.info("Запрос на получение блюда c chef null по ID: {}, {}", chefId, getCurrentUsername());
        return customerOrderRepository.findByChefIsNull();
    }
    @GetMapping("/waitersIsNull")
    public List<CustomerOrder> getOrdersWithNullWaiter(@RequestParam Long waiterId) throws NotFoundException {
        log.info("Запрос на получение блюда c waiter null по ID: {}, {}", waiterId, getCurrentUsername());
        return customerOrderRepository.findByChefIsNull();
    }
    @GetMapping("/waitersAndChefsNull")
    public List<CustomerOrder> getOrdersWithNullWaiterAndChef(@RequestParam Long chefId) throws NotFoundException {
        log.info("Запрос на получение блюда c waiter и chef null по ID: {}, {}", chefId, getCurrentUsername());
        return customerOrderRepository.findByChefIsNull();
    }
}//в customerOrder кладу конкретного 1)chef 2)waiter
//в customerOrder кладу конкретного chef и waiter


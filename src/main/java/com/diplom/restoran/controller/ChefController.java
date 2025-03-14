package com.diplom.restoran.controller;

import com.diplom.restoran.dto.ChefDTO;
import com.diplom.restoran.entity.Chef;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.ChefRepository;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.service.ChefService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chef")
@Tag(name = "Chef", description = "Управление поварами")
@PreAuthorize("hasRole('CHEF')")
public class ChefController {
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
        return chefService.getChefById(id);
}
@GetMapping
    public List<ChefDTO> findAllChefs() {return chefService.getAllChefs();}
    @GetMapping("/chefsIsNull")
    public List<CustomerOrder> getOrdersWithNullChef(@RequestParam Long chefId) throws NotFoundException {
        return customerOrderRepository.findByChefIsNull();
    }
    @GetMapping("/waitersIsNull")
    public List<CustomerOrder> getOrdersWithNullWaiter(@RequestParam Long chefId) throws NotFoundException {
        return customerOrderRepository.findByChefIsNull();
    }
    @GetMapping("/waitersAndChefsNull")
    public List<CustomerOrder> getOrdersWithNullWaiterAndChef(@RequestParam Long chefId) throws NotFoundException {
        return customerOrderRepository.findByChefIsNull();
    }
}//в customerOrder кладу конкретного 1)chef 2)waiter
//в customerOrder кладу конкретного chef и waiter


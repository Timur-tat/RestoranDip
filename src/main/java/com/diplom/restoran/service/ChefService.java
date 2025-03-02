package com.diplom.restoran.service;

import com.diplom.restoran.dto.ChefDTO;
import com.diplom.restoran.entity.Chef;
import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.entity.Dish;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.ChefRepository;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChefService {
    private final ChefRepository chefRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private CustomerRepository customerRepository;


    public ChefService(ChefRepository chefRepository, CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository){
        this.chefRepository = chefRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
    }
    public List<ChefDTO> getAllChefs(){
        return chefRepository.findAll().stream().map(chef -> new ChefDTO(chef.getId(),chef.getName(), null)).collect(Collectors.toList());
    }
    public Chef saveChef(ChefDTO chefDTO){
        Chef chef = new Chef();
        chef.setName(chefDTO.getName());
     List<CustomerOrder> customerOrders = chefDTO.getOrderIds().stream()
                     .map(id->customerOrderRepository.findById(id).orElseThrow(()->new NotFoundException(""))).collect(Collectors.toList());

        chef.setOrders(customerOrders);
        return chefRepository.save(chef);

    }
//    public Chef saveCustomerOrderToChef(Long chefId, List<List> orderIds){
//        Chef chef = chefRepository.findById(chefId).orElseThrow(()->new NotFoundException(""+ chefId));
//        List<CustomerOrder> customerOrders=orderIds.stream()
//                .map(id-> customerOrderRepository.findById().orElseThrow(()->new NotFoundException("")).collect(Collectors.toList()));
//        chef.setOrders(customerOrders);
//        return chefRepository.save(chef);
//
//    }
//public Customer saveCustomerOrderToCustomer(Long customerId, Long customerorderIds){
//    Customer customer = customerRepository.findById(customerId).orElseThrow(()->new NotFoundException(""+customerId));
//    CustomerOrder customerOrder = customerOrderRepository.findById(customerorderIds).orElseThrow(()->new NotFoundException(""+customerorderIds));
//    customerOrder.setCustomer(customer);
//    customerOrderRepository.save(customerOrder);
//    return customerOrder.getCustomer();
//
//}
    public ChefDTO getChefById(Long id) throws NotFoundException{
        Optional<Chef> optionalChef = chefRepository.findById(id);
        if (optionalChef.isEmpty()){
            throw new NotFoundException("Chef not found");
        }
        Chef chef = optionalChef.get();
        return new ChefDTO(chef.getId(), chef.getName(), chef.getOrders().stream().map(x->x.getId()).collect(Collectors.toList()));

    }
public Chef saveCustomerOrderToChef(Long customerorderId, Long chefId) {
    Chef chef = chefRepository.findById(chefId)
            .orElseThrow(() -> new NotFoundException("Chef not found with id: " + chefId));

    CustomerOrder customerOrder = customerOrderRepository.findById(customerorderId)
            .orElseThrow(() -> new NotFoundException("CustomerOrder not found with id: " + customerorderId));

    chef.addToOrders(customerOrder);
    return chefRepository.save(chef);

}


}

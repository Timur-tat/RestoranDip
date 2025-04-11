package com.diplom.restoran.service;

import com.diplom.restoran.dto.CustomerDTO;
import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository) {

        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    public List<CustomerDTO> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll().stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), null)).collect(Collectors.toList());
    } //рабочий
//public List<CustomerDTO> getAllCustomers() {
//    log.info("Запрос на получение всех клиентов");
//
//    List<CustomerDTO> customerDTOs = customerRepository.findAll().stream()
//            .map(customer -> {
//                log.debug("Преобразование клиента в DTO: {}", customer);
//                CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), null);
//                return customerDTO;
//            })
//            .collect(Collectors.toList());
//
//    log.debug("Получено {} клиентов", customerDTOs.size());
//    return customerDTOs;
//}
    public Customer saveCustomer(CustomerDTO customerDTO) {
        log.info("Save customer");
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        if (!customerDTO.getCustomerOrderIds().isEmpty()){
        List<CustomerOrder> customerOrders = customerDTO.getCustomerOrderIds().stream()
                .map(id->customerOrderRepository.findById(id).orElseThrow(()->new NotFoundException(""))).collect(Collectors.toList());

        customer.setOrders(customerOrders);
        }
        return customerRepository.save(customer);
}
public Customer saveCustomerOrderToCustomer(Long customerId, Long customerOrderIds){
        log.info("Save customer order");
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new NotFoundException(""+customerId));
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderIds).orElseThrow(()->new NotFoundException(""+customerOrderIds));
        customerOrder.setCustomer(customer);
        customer.addToOrders(customerOrder);
        return customerRepository.save(customer);

}
    public Customer removeCustomerOrderFromCustomer(Long customerId, Long customerOrderId){
        log.info("Remove customer order");
        Customer customer = customerRepository.findById(customerId).orElseThrow(()->new NotFoundException(""+customerId));
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId).orElseThrow(()->new NotFoundException(""+customerOrderId));
        customer.removeFromOrders(customerOrder);
        return customerRepository.save(customer);
}
public Double setAllPriceTotalAmount(Long customerOrderId){
        log.info("Set all price total amount");
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId).orElseThrow(()->new NotFoundException(""+customerOrderId));
        customerOrder.setAllPriceTotalAmount();
        return customerOrder.getTotalAmount();



}
}

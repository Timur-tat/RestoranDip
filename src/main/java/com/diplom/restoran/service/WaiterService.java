package com.diplom.restoran.service;

import com.diplom.restoran.dto.WaiterDTO;
import com.diplom.restoran.entity.CustomerOrder;
import com.diplom.restoran.entity.Waiter;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.CustomerOrderRepository;
import com.diplom.restoran.repository.CustomerRepository;
import com.diplom.restoran.repository.WaiterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class WaiterService {
private final WaiterRepository waiterRepository;
private CustomerOrderRepository customerOrderRepository;
private CustomerRepository customerRepository;
public WaiterService(WaiterRepository waiterRepository, CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository) {this.waiterRepository=waiterRepository;
this.customerOrderRepository=customerOrderRepository;
this.customerRepository=customerRepository;}
    public List<WaiterDTO> getAllWaiters() {
    return waiterRepository.findAll().stream()
            .map(waiter -> new WaiterDTO(waiter.getId(),waiter.getName(),null)).collect(Collectors.toList());
    }

    public WaiterDTO getWaiterByIdDTO(Long id) throws NotFoundException {
    log.info("getWaiterByIdDTO");
    Optional<Waiter> optionalWaiter=waiterRepository.findById(id);
    if(optionalWaiter.isEmpty()){
        throw new NotFoundException("Waiter");
    }
   Waiter waiter=optionalWaiter.get();
    return new WaiterDTO(waiter.getId(),waiter.getName(),null);
    }

//    public Waiter getWaiterById(Long id) throws NotFoundException {
//        Optional<Waiter> optionalWaiter=waiterRepository.findById(id);
//        if(optionalWaiter.isEmpty()){
//            throw new NotFoundException("Waiter");
//        }
//        Waiter waiter=optionalWaiter.get();
//        return  waiter;
//    } //рабочий
public Waiter getWaiterById(Long id) throws NotFoundException {
    log.info("Запрос на получение официанта по ID: {}", id);

    Optional<Waiter> optionalWaiter = waiterRepository.findById(id);

    if (optionalWaiter.isEmpty()) {
        log.error("Официант с ID {} не найден", id);
        throw new NotFoundException("Официант с ID " + id + " не найден");
    }

    Waiter waiter = optionalWaiter.get();
    log.debug("Найден официант: {}", waiter);

    return waiter;
}


    public Waiter saveWaiter(WaiterDTO waiterDTO) {
    log.info("saveWaiter");
    Waiter waiter = new Waiter();
    waiter.setName(waiterDTO.getName());
    return waiterRepository.save(waiter);
    }

    public Waiter saveWaiter(Waiter waiter){
    log.info("saveWaiter");
    return waiterRepository.save(waiter);
    }

    public Waiter saveCustomerOrderToWaiter(Long waiterId, Long customerOrderIds){
    log.info("saveCustomerOrderToWaiter");
        Waiter waiter = waiterRepository.findById(waiterId).orElseThrow(() -> new NotFoundException("Customer order not found"));
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderIds).orElseThrow(()->new NotFoundException(""+customerOrderIds));
      waiter.addToOrders(customerOrder);
      Waiter waiter1=waiterRepository.save(waiter);
        return waiter1;

    }

    public Waiter removeCustomerOrderFromWaiter(Long waiterId, Long customerOrderIds){
    log.info("removeCustomerOrderFromWaiter");
        Waiter waiter = waiterRepository.findById(waiterId).orElseThrow(()->new NotFoundException(""));
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderIds).orElseThrow(()->new NotFoundException(""+customerOrderIds));
      waiter.removeFromOrders(customerOrder);
      return waiterRepository.save(waiter);

    }


}

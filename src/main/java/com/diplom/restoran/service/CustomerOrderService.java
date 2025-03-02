package com.diplom.restoran.service;

import com.diplom.restoran.dto.CustomerOrderDTO;
import com.diplom.restoran.entity.*;
import com.diplom.restoran.exeption.NotFoundException;
import com.diplom.restoran.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;
    private DishRepository dishRepository;
    private ChefRepository chefRepository;
    private WaiterRepository waiterRepository;
    private Random random = new Random();
    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, DishRepository dishRepository, ChefRepository chefRepository, WaiterRepository waiterRepository, CustomerRepository customerRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.dishRepository= dishRepository;
        this.chefRepository= chefRepository;
        this.waiterRepository= waiterRepository;
        this.customerRepository= customerRepository;

        ;
    }

    public List<CustomerOrder> getAllCustomerOrders() {
return customerOrderRepository.findAll();
    }

    public CustomerOrderDTO getCustomerOrderByIdDTO(Long id) throws NotFoundException {
        Optional<CustomerOrder> optionalCustomerOrder = customerOrderRepository.findById(id);
        if (optionalCustomerOrder.isEmpty()){
            throw new NotFoundException("Customer order not found");
        }
        CustomerOrder customerOrder = optionalCustomerOrder.get();
        return new CustomerOrderDTO(null, customerOrder.getStatus(), customerOrder.getId(), null, null ,
                customerOrder.getTotalAmount(), customerOrder.getPaid());
    }

    public CustomerOrder getCustomerOrderById(Long id) throws NotFoundException {
        Optional<CustomerOrder> optionalCustomerOrder = customerOrderRepository.findById(id);
        if (optionalCustomerOrder.isEmpty()){
            throw new NotFoundException("Customer order not found");
        }
        CustomerOrder customerOrder = optionalCustomerOrder.get();
        return customerOrder;
    }
    public List<CustomerOrder> getOrdersWithNullWaiter() {
        return customerOrderRepository.findByWaiterIsNull();
    }

    public List<CustomerOrder> getOrdersWithNullChef() {
        return customerOrderRepository.findByChefIsNull();
    }

    public List<CustomerOrder> getOrdersWithNullWaiterAndChef() {
        return customerOrderRepository.findByWaiterIsNullAndChefIsNull();
    }

    public List<Dish> getDishesByCustomerOrderId(Long customerOrderId) throws NotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId)
                .orElseThrow(() -> new NotFoundException("Customer order not found with id: " + customerOrderId));

        return customerOrder.getDishes(); // Возвращаем список Dishes из CustomerOrder
    }
public CustomerOrder addChefToCustomerOrder(Long customerOrderId, Long chefId) throws NotFoundException {
        CustomerOrder   customerOrder = customerOrderRepository.findById(customerOrderId)
                .orElseThrow(() -> new NotFoundException("Customer order not found with id: " + customerOrderId));
        Chef chef=chefRepository.findById(chefId)
                .orElseThrow(() -> new NotFoundException("Chef not found with id: " + chefId));
        customerOrder.setChef(chef);
      return   customerOrderRepository.save(customerOrder);
}
    public CustomerOrder addWaiterToCustomerOrder(Long customerOrderId, Long waiterId) throws NotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId)
                .orElseThrow(() -> new NotFoundException("Customer order not found with id: " + customerOrderId));

        Waiter waiter = waiterRepository.findById(waiterId)
                .orElseThrow(() -> new NotFoundException("Waiter not found with id: " + waiterId));

        customerOrder.setWaiter(waiter);
        return customerOrderRepository.save(customerOrder);
    }
public List <CustomerOrder> customerOrderWithNullWaiter(){
        return customerOrderRepository.findByWaiterIsNull();
}

public List <CustomerOrder> customerOrderWithNullChef(){
        return customerOrderRepository.findByChefIsNull();
}
public Double setAllPriceTotalAmount(Long customerOrderId) throws NotFoundException {
        Optional<CustomerOrder> optionalCustomerOrder = customerOrderRepository.findById(customerOrderId);
        CustomerOrder customerOrder;
        if (optionalCustomerOrder.isEmpty()){

            throw new NotFoundException("Customer order not found with id: " + customerOrderId);
        }else {
            customerOrder = optionalCustomerOrder.get();
            customerOrder.setAllPriceTotalAmount();
            customerOrderRepository.save(customerOrder);
        }
        return customerOrder.getTotalAmount();



    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
public CustomerOrder saveCustomerOrder(CustomerOrderDTO customerOrderDTO) throws NotFoundException {
    Customer customer = customerRepository.findById(customerOrderDTO.getCustomerId())
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    List<Dish> dishes = customerOrderDTO.getDishIds().stream()
            .map(dish -> dishRepository.findById(dish)
                    .orElseThrow(() -> new NotFoundException("Dish not found")))
            .toList();

    CustomerOrder newOrder = new CustomerOrder(null, null, customerOrderDTO.getStatus(),customer, null, dishes, 0.0, false);
    CustomerOrder savedOrder = customerOrderRepository.save(newOrder);
    return savedOrder;
}
    public void addDishToOrder(Long orderId, Long dishId) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new NotFoundException("Dish not found"));

        if (order.getDishes().contains(dish)) {
            throw new IllegalArgumentException("Dish already added to order");
        }

        order.getDishes().add(dish);
        order.setAllPriceTotalAmount();
        customerOrderRepository.save(order);
    }
    public CustomerOrder saveCustomerOrder(CustomerOrder customerOrder) throws NotFoundException {
        Optional<CustomerOrder> optionalCustomerOrder = customerOrderRepository.findById(customerOrder.getId());
        if (optionalCustomerOrder.isEmpty()){
            throw new NotFoundException("Customer order not found with id: " + customerOrder.getId());
        }else {
            customerOrder = optionalCustomerOrder.get();
        }
        return customerOrderRepository.save(customerOrder);
    }
    public CustomerOrder paidIsTrue( Long customerOrderId) throws NotFoundException {
        CustomerOrder customerOrder = customerOrderRepository.findById(customerOrderId)
                .orElseThrow(() -> new NotFoundException("Заказ с таким полем Id отсутствует" + customerOrderId));

        customerOrder.setPaid(true);
        return customerOrderRepository.save(customerOrder);
    }
    public void deleteCustomerOrder(Long id) {
        if (!customerOrderRepository.existsById(id)) {
            throw new NotFoundException("CustomerOrder not found with id: " + id);
        }
        customerOrderRepository.deleteById(id);
    }
}

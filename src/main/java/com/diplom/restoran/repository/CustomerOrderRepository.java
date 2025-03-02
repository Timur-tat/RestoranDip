package com.diplom.restoran.repository;

import com.diplom.restoran.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, PagingAndSortingRepository<CustomerOrder, Long> {
public Optional<CustomerOrder> findById(Long id);
    // Метод для поиска заказов, где waiter = null И chef = null
    List<CustomerOrder> findByWaiterIsNullAndChefIsNull();

    // Альтернативный вариант с использованием @Query
//    @Query("SELECT o FROM CustomerOrder o WHERE o.waiter IS NULL AND o.chef IS NULL")
//    List<CustomerOrder> findOrdersWithNullWaiterAndChef();

    // Метод для поиска заказов, где chef = null
    List<CustomerOrder> findByChefIsNull();

    // Альтернативный вариант с использованием @Query
//    @Query("SELECT o FROM CustomerOrder o WHERE o.chef IS NULL")
//    List<CustomerOrder> findOrdersWithNullChef();

    // Метод для поиска заказов, где waiter = null
    List<CustomerOrder> findByWaiterIsNull();

    // Альтернативный вариант с использованием @Query
//    @Query("SELECT o FROM CustomerOrder o WHERE o.waiter IS NULL")
//    List<CustomerOrder> findOrdersWithNullWaiter();

}

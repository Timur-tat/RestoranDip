package com.diplom.restoran.repository;

import com.diplom.restoran.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, PagingAndSortingRepository<CustomerOrder, Long> {
}

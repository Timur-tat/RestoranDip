package com.diplom.restoran.repository;


import com.diplom.restoran.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long>, PagingAndSortingRepository<CustomerOrder, Long> {
}

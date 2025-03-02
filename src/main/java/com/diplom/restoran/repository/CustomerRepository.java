package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Customer;
import com.diplom.restoran.security.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer>, PagingAndSortingRepository<Customer,Integer> {
    public Optional<Customer> findById(Long id);

    Optional<Customer> findByUser(User user);
}

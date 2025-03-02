package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface WaiterRepository extends JpaRepository<Waiter, Long>, PagingAndSortingRepository<Waiter, Long> {
    public Optional<Waiter> findById (Long id);
}

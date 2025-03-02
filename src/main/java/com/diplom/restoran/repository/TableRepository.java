package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<Table, Long>, PagingAndSortingRepository<Table, Long> {
    public Optional<Table> findById(Long id);
}

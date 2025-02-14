package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TableRepository extends JpaRepository<Table, Integer>, PagingAndSortingRepository<Table, Integer> {
}

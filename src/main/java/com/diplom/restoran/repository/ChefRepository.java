package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ChefRepository extends JpaRepository<Chef,Long>, PagingAndSortingRepository<Chef,Long> {
    public Optional<Chef> findById(Long id);
}

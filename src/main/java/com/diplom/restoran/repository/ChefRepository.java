package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Chef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChefRepository extends JpaRepository<Chef,Integer>, PagingAndSortingRepository<Chef,Integer> {
}

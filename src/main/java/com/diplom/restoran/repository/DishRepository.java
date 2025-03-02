package com.diplom.restoran.repository;


import com.diplom.restoran.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long>, PagingAndSortingRepository<Dish, Long> {
    public Optional<Dish> findById(Long id);

    List<Dish> findByPriceLessThan(Double maxPrice);
}

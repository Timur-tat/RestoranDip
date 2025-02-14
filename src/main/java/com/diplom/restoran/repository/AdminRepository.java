package com.diplom.restoran.repository;

import com.diplom.restoran.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>, PagingAndSortingRepository<Admin, Integer> {
}

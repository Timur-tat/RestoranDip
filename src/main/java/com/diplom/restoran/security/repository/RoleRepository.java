package com.diplom.restoran.security.repository;


import com.diplom.restoran.security.models.ERole;
import com.diplom.restoran.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}

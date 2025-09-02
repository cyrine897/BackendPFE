package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.ERole;
import com.example.sahtyapp1.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}

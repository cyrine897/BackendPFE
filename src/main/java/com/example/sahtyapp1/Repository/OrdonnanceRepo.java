package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Ordonnance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdonnanceRepo  extends JpaRepository<Ordonnance, Long> {
}

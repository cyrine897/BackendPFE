package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RDVRepo  extends JpaRepository<RDV, Long> {
}

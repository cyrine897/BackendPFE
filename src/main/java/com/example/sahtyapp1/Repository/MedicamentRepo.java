package com.example.sahtyapp1.Repository;
import com.example.sahtyapp1.Entity.DossierMedical;
import com.example.sahtyapp1.Entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicamentRepo extends JpaRepository<Medicament, Long>  {

}

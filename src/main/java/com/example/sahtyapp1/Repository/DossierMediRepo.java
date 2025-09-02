package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.DossierMedical;
import com.example.sahtyapp1.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DossierMediRepo extends JpaRepository<DossierMedical , Long> {
    List<DossierMedical> findByFileName(String fileName);
    DossierMedical findByUtilisateur(Utilisateur utilisateur);
    Optional<DossierMedical> findByUtilisateurEmail(String email);


}

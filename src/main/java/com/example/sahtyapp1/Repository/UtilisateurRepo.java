package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepo  extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);

    Utilisateur findByEmail(String email);
    List<Utilisateur> findByRolee(String rolee);
    Utilisateur findByResetToken(String resetToken);
    List<Utilisateur> findByUserRoleMedNotNull();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

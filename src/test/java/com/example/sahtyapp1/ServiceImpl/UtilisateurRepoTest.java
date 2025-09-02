package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // utilise H2 en mémoire
class UtilisateurRepoTest {

    @Autowired
    private UtilisateurRepo utilisateurRepo;

    @Test
    @DisplayName("Should save and find user by username")
    void shouldFindByUsername() {
        Utilisateur user = Utilisateur.builder()
                .username("jdoe")
                .email("jdoe@example.com")
                .nom("Doe")
                .prenom("John")
                .adresse("123 Rue")
                .password("secret")
                .date_naissance(LocalDate.of(1990, 1, 1))
                .build();

        utilisateurRepo.save(user);

        Optional<Utilisateur> found = utilisateurRepo.findByUsername("jdoe");

        assertThat(found)
                .as("User with username 'jdoe' should exist")
                .isPresent();

        assertThat(found.get().getEmail())
                .isEqualTo("jdoe@example.com");
    }

    @Test
    @DisplayName("Should check existence by email")
    void shouldCheckExistsByEmail() {
        Utilisateur user = Utilisateur.builder()
                .username("asma")
                .email("asma@example.com")
                .password("password")
                .build();

        utilisateurRepo.save(user);

        boolean exists = utilisateurRepo.existsByEmail("asma@example.com");

        assertThat(exists)
                .as("User with email 'asma@example.com' should exist")
                .isTrue();
    }

    @Test
    @DisplayName("Should find users by role")
    void shouldFindByRole() {
        Utilisateur user = Utilisateur.builder()
                .username("khaled")
                .email("khaled@example.com")
                .rolee("ROLE_MEDECIN")
                .password("azerty")
                .build();

        utilisateurRepo.save(user);

        var users = utilisateurRepo.findByRolee("ROLE_MEDECIN");

        assertThat(users)
                .as("At least one user with role 'ROLE_MEDECIN' should be found")
                .isNotEmpty();

        assertThat(users.get(0).getUsername())
                .isEqualTo("khaled");
    }
}

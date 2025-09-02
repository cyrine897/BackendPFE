package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)

public class UtilisateurServiceTest {

        @Mock
        private UtilisateurRepo utilisateurRepository;

        @InjectMocks
        private UtilisateurDetailServiceImpl utilisateurService;

        @Test
        void testFindByUsername() {
            Utilisateur user = new Utilisateur();
            user.setUsername("admin");

            Mockito.when(utilisateurRepository.findByUsername("admin")).thenReturn(Optional.of(user));

            Optional<Utilisateur> result = utilisateurService.findByUsername("admin");

            assertTrue(result.isPresent());
            assertEquals("admin", result.get().getUsername());
        }

}

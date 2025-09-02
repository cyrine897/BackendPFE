package com.example.sahtyapp1.serviceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.sahtyapp1.Entity.*;
import com.example.sahtyapp1.Repository.*;
import com.example.sahtyapp1.Security.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DossierMedicaleServiceImplTest {

        @InjectMocks
        DossierMedicaleServiceImpl dossierMedicaleService;

        @Mock
        DossierMediRepo dossierMediRepo;

        @Mock
        UtilisateurRepo utilisateurRepo;

        @Test
        void testUploadFile_success() throws IOException {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setIdUser(1L);

            MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "Hello World".getBytes());

            when(utilisateurRepo.findById(1L)).thenReturn(Optional.of(utilisateur));
            when(dossierMediRepo.save(any(DossierMedical.class))).thenAnswer(i -> i.getArgument(0));

            String result = dossierMedicaleService.uploadFile(mockFile, 1L);
            assertNotNull(result);
            assertTrue(result.contains("File uploaded successfully"));
        }

        @Test
        void testGetDossierMedicalByEmail_found() {
            DossierMedical dossier = new DossierMedical();
            when(dossierMediRepo.findByUtilisateurEmail("test@example.com")).thenReturn(Optional.of(dossier));

            DossierMedical result = dossierMedicaleService.getDossierMedicalByEmail("test@example.com");
            assertNotNull(result);
        }

        @Test
        void testGetDossierMedicalByEmail_notFound() {
            when(dossierMediRepo.findByUtilisateurEmail("notfound@example.com")).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> dossierMedicaleService.getDossierMedicalByEmail("notfound@example.com"));
        }

        @Test
        void testDownloadFiles_returnsList() {
            DossierMedical doss = new DossierMedical();
            doss.setDossierMedical(new byte[]{1, 2, 3});
            when(dossierMediRepo.findByFileName("test.pdf")).thenReturn(List.of(doss));

            List<byte[]> result = dossierMedicaleService.downloadFiles("test.pdf");
            assertEquals(1, result.size());
            assertArrayEquals(new byte[]{1, 2, 3}, result.get(0));
        }

    @Test
    void testUploadFile_userNotFound() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "Hello World".getBytes());
        when(utilisateurRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> dossierMedicaleService.uploadFile(mockFile, 1L));
    }

    @Test
    void testGetDossierMedicalByUserId_found() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setIdUser(1L);

        DossierMedical dossier = new DossierMedical();

        when(utilisateurRepo.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(dossierMediRepo.findByUtilisateur(utilisateur)).thenReturn(dossier);

        DossierMedical result = dossierMedicaleService.getDossierMedicalByUserId(1L);
        assertNotNull(result);
    }

    @Test
    void testGetDossierMedicalByUserId_userNotFound() {
        when(utilisateurRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> dossierMedicaleService.getDossierMedicalByUserId(1L));
    }

    @Test
    void testSaveDossierMedical() {
        DossierMedical dossier = new DossierMedical();
        when(dossierMediRepo.save(dossier)).thenReturn(dossier);

        DossierMedical result = dossierMedicaleService.saveDossierMedical(dossier);
        assertEquals(dossier, result);
    }

    @Test
    void testDownloadFiles_emptyList() {
        when(dossierMediRepo.findByFileName("nonexistent.pdf")).thenReturn(Collections.emptyList());

        List<byte[]> result = dossierMedicaleService.downloadFiles("nonexistent.pdf");
        assertTrue(result.isEmpty());
    }


}

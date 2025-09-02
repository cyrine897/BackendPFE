package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.DossierMedical;
import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.DossierMediRepo;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import com.example.sahtyapp1.Security.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DossierMedicaleServiceImpl {
    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Autowired
    DossierMediRepo dossierMediRepo;

  public String uploadFile(MultipartFile file, Long idUser) throws IOException {
      Optional<Utilisateur> utilisateur = utilisateurRepo.findById(idUser);
      if (!utilisateur.isPresent()) {
          throw new EntityNotFoundException("Utilisateur not found with id " + idUser);
      }

      DossierMedical dossierMedical = dossierMediRepo.save(DossierMedical.builder()
              .fileName(file.getOriginalFilename())
              .type(file.getContentType())
              .dossierMedical(FileUtils.compressFile(file.getBytes()))
              .utilisateur(utilisateur.get())
              .build());

      if (dossierMedical != null) {
          return "File uploaded successfully: " + file.getOriginalFilename();
      }
      return null;
  }


    public DossierMedical saveDossierMedical(DossierMedical dossierMedical) {
        return dossierMediRepo.save(dossierMedical);
    }
    public DossierMedical getDossierMedicalByEmail(String email) {
        return dossierMediRepo.findByUtilisateurEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Dossier médical not found for email: " + email));
    }
    public DossierMedical getDossierMedicalByUserId(Long idUser) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(idUser);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            return dossierMediRepo.findByUtilisateur(utilisateur);
        } else {
            throw new IllegalArgumentException("User not found with id " + idUser);
        }
    }


    // Dans votre service DossierMedicaleServiceImpl
    public List<byte[]> downloadFiles(String fileName) {
        List<DossierMedical> files = dossierMediRepo.findByFileName(fileName);
        return files.stream().map(DossierMedical::getDossierMedical).collect(Collectors.toList());
    }

}

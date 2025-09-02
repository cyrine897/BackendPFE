package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.Consultation;
import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.ConsultationRepo;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import com.example.sahtyapp1.SeviceInterf.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    @Autowired
  private   ConsultationRepo consultationRepo;
    @Autowired
    private UtilisateurRepo utilisateurRepo;

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepo.save(consultation);
    }

    public List<Consultation> getConsultationsByUtilisateur(Long idUser) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepo.findById(idUser);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            return utilisateur.getConsultations();
        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            throw new RuntimeException("Utilisateur non trouvé avec l'ID " + idUser);
        }
    }

    //
}

package com.example.sahtyapp1.SeviceInterf;

import com.example.sahtyapp1.Entity.Consultation;

import java.util.List;

public interface ConsultationService {
    Consultation saveConsultation(Consultation consultation);
    List<Consultation> getConsultationsByUtilisateur(Long idUser);
}

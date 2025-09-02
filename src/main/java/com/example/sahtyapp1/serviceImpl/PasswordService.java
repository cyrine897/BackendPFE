package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import com.example.sahtyapp1.SeviceInterf.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class PasswordService implements Password {


    private final UtilisateurRepo utilisateurRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(UtilisateurRepo utilisateurRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.utilisateurRepo = utilisateurRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void generateResetToken(String email) {
        Utilisateur user = utilisateurRepo.findByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            utilisateurRepo.save(user);
            emailService.sendResetToken(email, token);
        }
    }

    public boolean resetPassword(String token, String newPassword) {
        Utilisateur utilisateur = utilisateurRepo.findByResetToken(token);
        if (utilisateur != null) {
            utilisateur.setPassword(passwordEncoder.encode(newPassword));
            utilisateur.setResetToken(null); // Invalidate the token after successful reset
            utilisateurRepo.save(utilisateur);
            return true;
        }
        return false;
    }

  /*  public boolean resetPassword(String token, String newPassword) {
        Utilisateur user = utilisateurRepo.findByResetToken(token);
        if (user != null) {
            user.setPassword(newPassword); // Vous devriez hacher le mot de passe ici
            user.setResetToken(null);
            utilisateurRepo.save(user);
            return true;
        }
        return false;
    }*/






}

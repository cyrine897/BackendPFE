package com.example.sahtyapp1.Controller;

import com.example.sahtyapp1.serviceImpl.PasswordService;
import com.example.sahtyapp1.serviceImpl.UtilisateurDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pass")
public class PasswordResetController {

    private final UtilisateurDetailServiceImpl utilisateurDetailService;
    private final PasswordService passwordService;

    @Autowired
    public PasswordResetController(UtilisateurDetailServiceImpl utilisateurDetailService, PasswordService passwordService) {
        this.utilisateurDetailService = utilisateurDetailService;
        this.passwordService = passwordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        passwordService.generateResetToken(request.getEmail());
        return ResponseEntity.ok("Reset token sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean success = passwordService.resetPassword(request.getToken(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Password successfully reset.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
    }
}

// DTOs (dans un package séparé, ex: dto)
class ForgotPasswordRequest {
    private String email;

    // getter + setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

class ResetPasswordRequest {
    private String token;
    private String newPassword;

    // getters + setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}

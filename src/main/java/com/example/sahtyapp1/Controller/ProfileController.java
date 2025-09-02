package com.example.sahtyapp1.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Le fichier est vide.";
        }

        // Nettoyage du nom de fichier
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Sanitize et validation
        if (originalFileName.contains("..") || originalFileName.contains("/") || originalFileName.contains("\\")) {
            return "Nom de fichier invalide.";
        }

        try {
            // Créer le répertoire s’il n’existe pas
            Path uploadPath = Paths.get(uploadDir).normalize();
            Files.createDirectories(uploadPath);

            // Générer un nom de fichier unique (UUID) pour éviter les injections
            String safeFileName = System.currentTimeMillis() + "_" + originalFileName;
            Path targetPath = uploadPath.resolve(safeFileName).normalize();

            // Vérifie que le chemin reste dans le répertoire autorisé
            if (!targetPath.startsWith(uploadPath)) {
                return "Chemin de fichier non autorisé.";
            }

            // Enregistrer le fichier
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "Fichier uploadé avec succès.";
        } catch (IOException e) {
            return "Erreur serveur lors de l'upload.";
        }
    }
}

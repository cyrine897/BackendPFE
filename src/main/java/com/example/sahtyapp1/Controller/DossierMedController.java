package com.example.sahtyapp1.Controller;

import com.example.sahtyapp1.Entity.ActivitePhysique;
import com.example.sahtyapp1.Entity.Consommation;
import com.example.sahtyapp1.Entity.DossierMedical;
import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.serviceImpl.DossierMedicaleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@RequestMapping("/api/dossierMedical")
@CrossOrigin(origins = "http://localhost:4200")
@ResponseBody
@RestController
public class DossierMedController {
    @Autowired
    DossierMedicaleServiceImpl dossierMedicaleService;


    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadFiles(@PathVariable String fileName) {
        List<byte[]> files = dossierMedicaleService.downloadFiles(fileName);

        if (files.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        } else if (files.size() == 1) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(files.get(0));
        } else {
            // Par exemple, retourner une liste d'URLs de fichiers ou une réponse personnalisée
            return ResponseEntity.status(HttpStatus.MULTI_STATUS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(files);  // Cela pourrait nécessiter une transformation en JSON valide
        }
    }

}

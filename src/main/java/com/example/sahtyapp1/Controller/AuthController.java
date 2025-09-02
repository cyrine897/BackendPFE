package com.example.sahtyapp1.Controller;

import com.example.sahtyapp1.Entity.*;
import com.example.sahtyapp1.Repository.*;
import com.example.sahtyapp1.Request.LoginRequest;
import com.example.sahtyapp1.Request.SignUpRequest;
import com.example.sahtyapp1.Response.JwtResponse;
import com.example.sahtyapp1.Response.MessageResponse;
import com.example.sahtyapp1.Security.JwtUtils;
import com.example.sahtyapp1.serviceImpl.*;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
@ResponseBody
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private final  UtilisateurRepo utilisateurRepo;
    @Autowired
    private  UserRoleMedRepo userRoleMedRepo;
    @Autowired
    private UserRolePharmRepo userRolePharmRepo;
    @Autowired
    private final  RoleRepo roleRepo;
    @Autowired
    private final  PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    DossierMedicaleServiceImpl dossierMedicaleService;
    @Autowired
    private UtilisateurDetailServiceImpl utilisateurDetailService;
    @Autowired
    private PasswordService passwordEncoderService;
    @Autowired
    private ConsultationServiceImpl consultationService;
    @Autowired
    DossierMediRepo dossierMediRepo;

    public AuthController(UtilisateurRepo utilisateurRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder ) {
        this.utilisateurRepo = utilisateurRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    private static final Logger logger = (Logger) LogManager.getLogger(AuthController.class);


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        passwordEncoderService.generateResetToken(email);
        return ResponseEntity.ok("Reset token sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        if (passwordEncoderService.resetPassword(token, newPassword)) {
            return ResponseEntity.ok("Password successfully reset.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token.");
        }
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        // your logic to handle the request
        return ResponseEntity.ok(utilisateur);
    }

   @GetMapping("/profile")
   public ResponseEntity<?> getUserProfile(@RequestParam String email) {
       try {
           Utilisateur utilisateur = utilisateurDetailService.findByEmail(email);
           if (utilisateur != null) {
               return ResponseEntity.ok(utilisateur);
           } else {
               return ResponseEntity.notFound().build();
           }
       } catch (Exception e) {
           // Log the exception for further investigation
           logger.error("Error retrieving user profile for email: " + email, e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving user profile.");
       }
   }
    @GetMapping("/utilisateurs")
    public List<Utilisateur> getUtilisateurByRoleMed(@RequestParam String rolee) {

       return utilisateurDetailService.getUtilisateursAvecRoleMedecin(rolee);
    }


@GetMapping("/test-utilisateurs")
public List<Utilisateur> testUtilisateurs() {
    return utilisateurRepo.findAll(); // juste pour tester la DB
}




    @PutMapping(value = "/updateProfile/{email}", consumes = "application/json", produces = "application/json")
public ResponseEntity<Utilisateur> updateProfile(
        @PathVariable String email,
        @RequestParam(required = false) Long idDossier,
        @RequestBody Utilisateur utilisateur) {

    try {
        Utilisateur existingUser = utilisateurDetailService.updateUtilisateur(email, utilisateur);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        DossierMedical dossierMedical;

        if (idDossier != null) {
            dossierMedical = dossierMediRepo.findById(idDossier).orElse(new DossierMedical());
            dossierMedical.setIdDossier(idDossier);

            if (utilisateur.getDossierMedical() != null) {
                DossierMedical input = utilisateur.getDossierMedical();
                dossierMedical.setNumeroUrgence(input.getNumeroUrgence());
                dossierMedical.setNomUrgence(input.getNomUrgence());
                dossierMedical.setTaille(input.getTaille());
                dossierMedical.setPoids(input.getPoids());
                dossierMedical.setActivitePhysique(input.getActivitePhysique());
                dossierMedical.setConsommation(input.getConsommation());
                dossierMedical.setFileName(input.getFileName());
                dossierMedical.setAnticedentsMedicaments(input.getAnticedentsMedicaments());
                dossierMedical.setDateVaccination(input.getDateVaccination());
                dossierMedical.setVaccination(input.getVaccination());
                dossierMedical.setFilePath(input.getFilePath());
            }

        } else if (existingUser.getDossierMedical() == null) {
            dossierMedical = new DossierMedical();
        } else {
            dossierMedical = existingUser.getDossierMedical();
        }

        dossierMedical.setUtilisateur(existingUser);
        existingUser.setDossierMedical(dossierMedical);
        dossierMediRepo.save(dossierMedical);

        return ResponseEntity.ok(existingUser);

    } catch (EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        // Logger can be used instead in production
        // log.error("Error updating profile", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UtilisateurDetailsImpl userDetails = (UtilisateurDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jwtResponse);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        // Check if username is already taken
        if (utilisateurRepo.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check if email is already in use
        if (utilisateurRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        Utilisateur utilisateur = new Utilisateur(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        utilisateur.setAdresse(signUpRequest.getAdresse());
        utilisateur.setNom(signUpRequest.getNom());
        utilisateur.setPrenom(signUpRequest.getPrenom());
        utilisateur.setDate_naissance(signUpRequest.getDate_naissance());
        utilisateur.setRolee(signUpRequest.getRolee());
        utilisateurRepo.save(utilisateur);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!", utilisateur.getIdUser()));
    }


    @PostMapping("/register/role/medecin")
    public ResponseEntity<?> registerRoleMedecin(@RequestParam Long idUser, @RequestBody SignUpRequest signUpRequest) {

        Optional<Utilisateur> utilisateurOptional = utilisateurRepo.findById(idUser);
        if (!utilisateurOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User not found.", null));
        }

        if (userRoleMedRepo.existsByPieceIdentiteMed(signUpRequest.getPieceIdentiteMed())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: PieceIdentiteMed is already in use!"));
        }

        Utilisateur utilisateur = utilisateurOptional.get();
        UserRoleMed userRoleMed = utilisateur.getUserRoleMed();

        // Create new UserRoleMed if not present
        if (userRoleMed == null) {
            userRoleMed = new UserRoleMed();
            utilisateur.setUserRoleMed(userRoleMed);
        }

        // Set Medecin details
        userRoleMed.setNumerodelicencedeMed(signUpRequest.getNumerodelicencedeMed());
        userRoleMed.setCopiedelalicencedeMed(signUpRequest.getCopiedelalicencedeMed());
        userRoleMed.setDiplomesMed(signUpRequest.getDiplomesMed());
        userRoleMed.setAdresseMed(signUpRequest.getAdresseMed());
        userRoleMed.setNumeroTelephoneMed(signUpRequest.getNumeroTelephoneMed());
        userRoleMed.setPositionMed(signUpRequest.getPositionMed());
        userRoleMed.setQualificationsMed(signUpRequest.getQualificationsMed());
        userRoleMed.setNomMed(signUpRequest.getNomMed());
        userRoleMed.setDateObtentionDeLaLicenceMed(signUpRequest.getDateObtentionDeLaLicenceMed());
        userRoleMed.setPieceIdentiteMed(signUpRequest.getPieceIdentiteMed());

        // Save the utilisateur and userRoleMed
        utilisateurRepo.save(utilisateur);  // Save Utilisateur to persist association
        userRoleMedRepo.save(userRoleMed);  // Save UserRoleMed

        return ResponseEntity.ok(new MessageResponse("Medecin details saved successfully!", utilisateur.getIdUser()));
    }

    @PostMapping("/register/role/pharmacien")
    public ResponseEntity<?> savePharmacienDetails(@RequestParam Long idUser, @Valid @RequestBody SignUpRequest signUpRequest) {
        Utilisateur utilisateur = utilisateurRepo.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!utilisateur.getRolee().equalsIgnoreCase("PHARMACIEN")) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User is not a Pharmacien."));
        }

        UserRolePharm userRolePharm = new UserRolePharm();
        userRolePharm.setNumerodelicencedePharm(signUpRequest.getNumerodelicencedePharm());
        userRolePharm.setCopiedelalicencedePharm(signUpRequest.getCopiedelalicencedePharm());
        userRolePharm.setDiplomesPharm(signUpRequest.getDiplomesPharm());
        userRolePharm.setAdressePharm(signUpRequest.getAdressePharm());
        userRolePharm.setNumeroTelephonePharm(signUpRequest.getNumeroTelephonePharm());
        userRolePharm.setPositionPharm(signUpRequest.getPositionPharm());
        userRolePharm.setQualificationsPharm(signUpRequest.getQualificationsPharm());
        userRolePharm.setNomPharm(signUpRequest.getNomPharm());
        userRolePharm.setDateObtentionDeLaLicencePharm(signUpRequest.getDateObtentionDeLaLicencePharm());
        userRolePharm.setPieceIdentitPharm(signUpRequest.getPieceIdentitPharm());

        utilisateur.setUserRolePharm(userRolePharm);
        userRolePharmRepo.save(userRolePharm);

        return ResponseEntity.ok(new MessageResponse("Pharmacien details saved successfully!", utilisateur.getIdUser()));
    }


   @GetMapping("/utilisateursByEmail")
    public Utilisateur getUsersByEmail(@RequestParam String email) {
        return utilisateurDetailService.getUsersByEmail(email);
    }

    @PutMapping("/verify-medecin/{idUser}")
    public ResponseEntity<Utilisateur> verifyMedecin(@PathVariable Long idUser) {
        try {
            Utilisateur utilisateur = utilisateurDetailService.findById(idUser); // Méthode pour trouver l'utilisateur par ID

            if (utilisateur != null && utilisateur.getRolee() != null) {
                // Vérifiez si le rôle "Medecin" est présent
                boolean isMedecin = utilisateur.getRolee().contains("Medecin");
                utilisateur.setVerifyMedecin(isMedecin);

                // Enregistrez les modifications si nécessaire
                utilisateurDetailService.save(utilisateur);
                if (isMedecin) {
                    sendVerificationEmail(utilisateur.getEmail());
                }

                // Répondre avec l'objet utilisateur mis à jour
                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void sendVerificationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Medecin Verified");
        message.setText("Congratulations! Your verification as a medic has been successful.");

        emailSender.send(message);
    }

    @Autowired
    private EmailService emailService;

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
        Utilisateur user = utilisateurRepo.findByResetToken(token);
        if (user != null) {
            user.setPassword(newPassword); // Vous devriez hacher le mot de passe ici
            user.setResetToken(null);
            utilisateurRepo.save(user);
            return true;
        }
        return false;
    }


    //upload photo
    private static final String UPLOAD_DIR = "uploads/";





    // Méthode pour charger les données binaires de l'image depuis une source
    private byte[] loadUserPhotoBytes(String photoUrl) {
        // Implémentation pour charger les données de l'image, par exemple à partir du système de fichiers
        // Ici, nous donnons un exemple avec un fichier statique
        try {
            Path path = Paths.get(photoUrl);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de l'image", e);
        }
    }

     
    @GetMapping("/photo/{email}")
public void getPhotoByEmail(@PathVariable String email, HttpServletResponse response) {
    Utilisateur utilisateur = utilisateurRepo.findByEmail(email);

    if (utilisateur == null || utilisateur.getPhoto() == null || utilisateur.getPhoto().isEmpty()) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return;
    }

    Path photoPath = Paths.get(UPLOAD_DIR, utilisateur.getPhoto());

    if (!Files.exists(photoPath) || !Files.isReadable(photoPath)) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return;
    }

    try {
        String contentType = Files.probeContentType(photoPath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "inline; filename=\"" + utilisateur.getPhoto() + "\"");

        Files.copy(photoPath, response.getOutputStream());
        response.getOutputStream().flush();
    } catch (IOException e) {
        // En production, utilise un logger au lieu de e.printStackTrace()
        // logger.error("Erreur lors de l'envoi du fichier photo", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}


    private String getContentType(Path path) throws IOException {
        String contentType = Files.probeContentType(path);
        return contentType != null ? contentType : "application/octet-stream";
    }

    private void copyFileToResponse(Path path, HttpServletResponse response) throws IOException {
        Files.copy(path, response.getOutputStream());
        response.getOutputStream().flush();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        // Invalidate the session
        SecurityContextHolder.clearContext();

        // You can also clear any specific tokens or session data here

        // Return an HTTP response with OK status
        return ResponseEntity.ok().build();
    }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idUser") Long idUser) throws IOException {
      String uploadImage = dossierMedicaleService.uploadFile(file, idUser);
      return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
  }



    @GetMapping("/dossiermedical")
    public ResponseEntity<?> getDossierMedicalByEmail(@RequestParam("email") String email) {
        DossierMedical dossierMedical = dossierMedicaleService.getDossierMedicalByEmail(email);
        return ResponseEntity.ok(dossierMedical);
    }


    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResponseStatusException ex) {
        return ex.getReason();
    }


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

//Consultation

    @PostMapping("/Addconsultations")
    public ResponseEntity<Consultation> addConsultation(@RequestBody Consultation consultation) {
        Consultation savedConsultation = consultationService.saveConsultation(consultation);
        return ResponseEntity.ok(savedConsultation);
    }
    @GetMapping("/{idUser}/consultations")
    public ResponseEntity<List<Consultation>> getConsultationsByUtilisateur(@PathVariable Long idUser) {
        List<Consultation> consultations = consultationService.getConsultationsByUtilisateur(idUser);
        return ResponseEntity.ok(consultations);
    }

}

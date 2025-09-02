package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Entity.DossierMedical;
import com.example.sahtyapp1.Entity.UserRoleMed;
import com.example.sahtyapp1.Entity.UserRolePharm;
import com.example.sahtyapp1.Entity.Utilisateur;
import com.example.sahtyapp1.Repository.UserRoleMedRepo;
import com.example.sahtyapp1.Repository.UserRolePharmRepo;
import com.example.sahtyapp1.Repository.UtilisateurRepo;
import com.example.sahtyapp1.Security.FileUtils;
import com.example.sahtyapp1.SeviceInterf.UtilisateurDetailService;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtilisateurDetailServiceImpl implements UtilisateurDetailService, UserDetailsService {

    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private UserRoleMedRepo userRoleMedRepo;

    @Autowired
    private UserRolePharmRepo userRolePharmRepo;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UtilisateurDetailsImpl.build(utilisateur);

    }
  /* public Utilisateur updateUtilisateur(String email ,Utilisateur utilisateur) {
        // Vérifiez si l'utilisateur existe déjà
        Utilisateur existingUser = utilisateurRepo.findByEmail(utilisateur.getEmail());
        if (existingUser == null) {
            // Gérer le cas où l'utilisateur n'existe pas ou lever une exception
            throw new RuntimeException("Utilisateur non trouvé pour l'email: " + utilisateur.getEmail());
        }

        // Mettre à jour les champs nécessaires
        existingUser.setNom(utilisateur.getNom());
        existingUser.setPrenom(utilisateur.getPrenom());
        existingUser.setAdresse(utilisateur.getAdresse());
        existingUser.setNumero(utilisateur.getNumero());
        existingUser.setDate_naissance(utilisateur.getDate_naissance());
        existingUser.setUsername(utilisateur.getUsername());
        existingUser.setPhoto(utilisateur.getPhoto());
        // Handle DossierMedical if needed
        if (utilisateur.getDossierMedical() != null) {
            existingUser.setDossierMedical(utilisateur.getDossierMedical());

        }
        // Enregistrer l'utilisateur mis à jour dans la base de données
        return utilisateurRepo.save(existingUser);
    }*/
  public List<Utilisateur> getUtilisateursAvecRoleMedecin(String rolee) {
      // Fetch all users where the UserRoleMed is not null (i.e., they have the role of Medecin)
      List<Utilisateur> utilisateurs = utilisateurRepo.findByUserRoleMedNotNull();

      if (utilisateurs.isEmpty()) {
          throw new RuntimeException("Aucun utilisateur avec le rôle de médecin trouvé");
      }

      // Filter the users based on the role, if needed
      List<Utilisateur> utilisateursMedecins = utilisateurs.stream()
              .filter(utilisateur -> utilisateur.getUserRoleMed() != null)
              .map(utilisateur -> {
                  // Create a copy of the Utilisateur object without sensitive information
                  Utilisateur utilisateur1 = new Utilisateur();
                  utilisateur1.setIdUser(utilisateur.getIdUser());
                  utilisateur1.setUsername(utilisateur.getUsername());
                  utilisateur1.setEmail(utilisateur.getEmail());
                  utilisateur1.setNumero(utilisateur.getNumero());
                  utilisateur1.setRolee(utilisateur.getRolee());

                  // Copy the Medecin role details
                  UserRoleMed userRoleMedCopy = new UserRoleMed();
                  UserRoleMed userRoleMed = utilisateur.getUserRoleMed();
                  userRoleMedCopy.setNumerodelicencedeMed(userRoleMed.getNumerodelicencedeMed());
                  userRoleMedCopy.setAdresseMed(userRoleMed.getAdresseMed());
                  userRoleMedCopy.setQualificationsMed(userRoleMed.getQualificationsMed());
                  userRoleMedCopy.setDiplomesMed(userRoleMed.getDiplomesMed());
                  userRoleMedCopy.setPieceIdentiteMed(userRoleMed.getPieceIdentiteMed());
                  userRoleMedCopy.setPositionMed(userRoleMed.getPositionMed());
                  userRoleMedCopy.setDateObtentionDeLaLicenceMed(userRoleMed.getDateObtentionDeLaLicenceMed());
                  userRoleMedCopy.setNumeroTelephoneMed(userRoleMed.getNumeroTelephoneMed());
                  userRoleMedCopy.setNomMed(userRoleMed.getNomMed());


                  // Assign the Medecin role to the new user object
                  utilisateur1.setUserRoleMed(userRoleMedCopy);

                  return utilisateur1;
              }).collect(Collectors.toList());

      return utilisateursMedecins;
  }


  /*  public Utilisateur getUtilisateurAvecRoleMedecin(Long idUser , String rolee) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepo.findById(idUser);
        if (!utilisateurOpt.isPresent()) {
            throw new RuntimeException("Utilisateur non trouvé");
        }

        Utilisateur utilisateur = utilisateurOpt.get();
        UserRoleMed userRoleMed = utilisateur.getUserRoleMed();

        if (userRoleMed == null) {
            throw new RuntimeException("Cet utilisateur n'a pas le rôle de médecin");
        }

        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setIdUser(utilisateur.getIdUser());
        utilisateur1.setUsername(utilisateur.getUsername());
        utilisateur1.setEmail(utilisateur.getEmail());
        utilisateur1.setNumero(utilisateur.getNumero());

        // Coordonnées du médecin
        UserRoleMed userRoleMedCopy = new UserRoleMed();
        userRoleMedCopy.setNumerodelicencedeMed(userRoleMed.getNumerodelicencedeMed());
        userRoleMedCopy.setAdresseMed(userRoleMed.getAdresseMed());
        userRoleMedCopy.setQualificationsMed(userRoleMed.getQualificationsMed());
        userRoleMedCopy.setNomMed(userRoleMed.getNomMed());
        userRoleMedCopy.setPieceIdentiteMed(userRoleMed.getPieceIdentiteMed());

        // Assigner le rôle médecin à l'utilisateur
        utilisateur1.setUserRoleMed(userRoleMedCopy);

        return utilisateur1;
    }*/


    public void creerUserRoleMed(Long  numerodelicencedeMed  , String  nomMed , String adresseMed ,
                               Long   numeroTelephoneMed , String  positionMed, String   qualificationsMed ,
                               Date dateObtentionDeLaLicenceMed ,
                               byte[] diplomesMed,
                               byte[] copiedelalicencedeMed , Long  pieceIdentiteMed  ){
      UserRoleMed userRoleMed = new UserRoleMed();
      userRoleMed.setNumerodelicencedeMed(numerodelicencedeMed);
      userRoleMed.setCopiedelalicencedeMed(copiedelalicencedeMed);
      userRoleMed.setDiplomesMed(diplomesMed);
      userRoleMed.setAdresseMed(adresseMed);
      userRoleMed.setNumeroTelephoneMed(numeroTelephoneMed);
      userRoleMed.setPositionMed(positionMed);
      userRoleMed.setQualificationsMed(qualificationsMed);
      userRoleMed.setNomMed(nomMed);
      userRoleMed.setDateObtentionDeLaLicenceMed(dateObtentionDeLaLicenceMed);
      userRoleMed.setPieceIdentiteMed(pieceIdentiteMed);

      }




    public void creerUserRolePharm(Long  numerodelicencePharm  , String  nomPharm , String adressePharm ,
                                 Long   numeroTelephonePharm , String  positionPharm, String   qualificationsPharm ,
                                 Long numerodelicencedePharm ,Date dateObtentionDeLaLicencePharm ,
                                   byte[] diplomesPharm,
                                   byte[] copiedelalicencedePharm , Long  pieceIdentitPharm  ){
        UserRolePharm userRolePharm = new UserRolePharm();
        userRolePharm.setNumerodelicencedePharm(numerodelicencedePharm);
        userRolePharm.setCopiedelalicencedePharm(copiedelalicencedePharm);
        userRolePharm.setDiplomesPharm(diplomesPharm);
        userRolePharm.setAdressePharm(adressePharm);
        userRolePharm.setNumeroTelephonePharm(numeroTelephonePharm);
        userRolePharm.setPositionPharm(positionPharm);
        userRolePharm.setQualificationsPharm(qualificationsPharm);
        userRolePharm.setNomPharm(nomPharm);
        userRolePharm.setDateObtentionDeLaLicencePharm(dateObtentionDeLaLicencePharm);
        userRolePharm.setPieceIdentitPharm(pieceIdentitPharm);

    }





  public Utilisateur updateUtilisateur(String email, Utilisateur utilisateur) {
      Utilisateur existingUser = utilisateurRepo.findByEmail(email);
      if (existingUser == null) {
          throw new RuntimeException("Utilisateur non trouvé pour l'email: " + email);
      }
      existingUser.setNom(utilisateur.getNom());
      existingUser.setPrenom(utilisateur.getPrenom());
      existingUser.setAdresse(utilisateur.getAdresse());
      existingUser.setNumero(utilisateur.getNumero());
      existingUser.setDate_naissance(utilisateur.getDate_naissance());
      existingUser.setUsername(utilisateur.getUsername());
      existingUser.setPhoto(utilisateur.getPhoto());


      if (utilisateur.getDossierMedical() != null) {
          DossierMedical existingDossier = existingUser.getDossierMedical();
          if (existingDossier == null) {
              existingDossier = new DossierMedical();
              existingDossier.setUtilisateur(existingUser);
              existingUser.setDossierMedical(existingDossier);
          }
          existingDossier.setNumeroUrgence(utilisateur.getDossierMedical().getNumeroUrgence());
          existingDossier.setNomUrgence(utilisateur.getDossierMedical().getNomUrgence());
          existingDossier.setTaille(utilisateur.getDossierMedical().getTaille());
          existingDossier.setPoids(utilisateur.getDossierMedical().getPoids());
          existingDossier.setFileName(utilisateur.getDossierMedical().getFileName());
          existingDossier.setActivitePhysique(utilisateur.getDossierMedical().getActivitePhysique());
          existingDossier.setConsommation(utilisateur.getDossierMedical().getConsommation());

          existingDossier.setAnticedentsMedicaments(utilisateur.getDossierMedical().getAnticedentsMedicaments());
          existingDossier.setDateVaccination(utilisateur.getDossierMedical().getDateVaccination());
          existingDossier.setVaccination(utilisateur.getDossierMedical().getVaccination());
          existingDossier.setFilePath(utilisateur.getDossierMedical().getFilePath());
      }
      return utilisateurRepo.save(existingUser);
  }

    public List<Utilisateur> getUsersByRole(String rolee) {
        return utilisateurRepo.findByRolee(rolee);
    }    public Utilisateur getUsersByEmail(String email) {
        return utilisateurRepo.findByEmail(email);
    }

    public Utilisateur findById(Long idUser) {
        return utilisateurRepo.findById(idUser).orElse(null);
    }
    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepo.save(utilisateur);
    }
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Augmenter la profondeur maximale autorisée
        StreamWriteConstraints constraints = StreamWriteConstraints.builder()
                .maxNestingDepth(2000) // Augmenter cette valeur selon vos besoins
                .build();
        mapper.getFactory().setStreamWriteConstraints(constraints);

        return mapper;
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepo.findByEmail(email);
    }

    public Optional<Utilisateur> findByUsername(String username) {
        return utilisateurRepo.findByUsername(username);
    }

    public void saveUtilisateur(Utilisateur utilisateur) {
        utilisateurRepo.save(utilisateur);
    }

  /*  @Autowired
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
    }*/




    @Transactional
    public Utilisateur createUtilisateurWithDossier(Utilisateur utilisateur, DossierMedical dossierMedical) {
        utilisateur.setDossierMedical(dossierMedical);
        return utilisateurRepo.save(utilisateur);
    }
    public String getUserPhotoUrl(Long idUser) {
        Optional<Utilisateur> userOptional = utilisateurRepo.findById(idUser);
        if (userOptional.isPresent()) {
            Utilisateur user = userOptional.get();
            return user.getPhotoUrl(); // Suppose que getPhotoUrl() retourne l'URL de la photo de l'utilisateur
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'ID: " + idUser);
        }
    }




    public String uploadFile(MultipartFile file, Long idUser) throws IOException {
        // Vérifier si l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepo.findById(idUser)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur not found with id " + idUser));

        // Compresser le fichier avant de le sauvegarder
        byte[] compressedFile = FileUtils.compressFile(file.getBytes());

        // Créer une nouvelle instance de UserRoleMed
        UserRoleMed userRoleMed = new UserRoleMed();
        userRoleMed.setDiplomesMed(compressedFile);

        // Sauvegarder l'objet dans la base de données
        userRoleMedRepo.save(userRoleMed);

        // Retourner un message de succès
        return "File uploaded successfully: " + file.getOriginalFilename();
    }



}

package com.example.sahtyapp1.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity

@Getter
@Setter
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "utilisateur", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})

public class Utilisateur implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;
    private String nom;
    private String username;
    private String prenom;
    private String adresse;
    private String email ;
    private String rolee ;
    private boolean verifyMedecin;  // New field
    private String password;
    private String resetToken;
    private String photo;
    private String photoUrl;
    private String photoname;
    private LocalDate date_naissance ;

    private Long numero ;


    public Utilisateur(String username, String email, String encode, String nom, String prenom, String adresse, LocalDate dateNaissance, Long numero, Set<Role> roles) {}

    public Utilisateur(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @JsonProperty("role")
    @NotNull
    @JsonIgnore

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))

    private Set<Role> roles = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_med_id", referencedColumnName = "id_rolemed")
    private UserRoleMed userRoleMed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_role_pharm_id", referencedColumnName = "id_rolepharm")
    private UserRolePharm userRolePharm;




    @ManyToMany
    @JsonIgnore

    @JoinTable(
            name = "utilisateur_message",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "message_id")
    )

    private Set<Message> messages = new HashSet<>();
    @ManyToMany
    @JsonIgnore

    @JoinTable(
            name = "utilisateur_evaluation",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "evaluation_id")
    )

    private Set<Evaluation> evaluations = new HashSet<>();
    @OneToOne
    @JsonIgnore

    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JoinColumn(name = "id_doss")
    @JsonBackReference
    private DossierMedical dossierMedical;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RDV> rdvs = new HashSet<>();
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    @JsonIgnore

    private Set<Ordonnance> ordonnances = new HashSet<>();
    @ManyToMany
    @JsonIgnore

    @JoinTable(
            name = "ordonnance_medicament",
            joinColumns = @JoinColumn(name = "ordonnance_id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id"))
    private Set<Medicament> medicaments = new HashSet<>();

    @JsonIgnore

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations = new ArrayList<>();

    public Utilisateur() {

    }

    public Utilisateur(Long idUser, String nom, String username, String prenom, String adresse, String email, String rolee, boolean verifyMedecin, String password, String resetToken, String photo, String photoUrl, String photoname, LocalDate date_naissance, Long numero, Set<Role> roles, UserRoleMed userRoleMed, UserRolePharm userRolePharm, Set<Message> messages, Set<Evaluation> evaluations, Profile profile, DossierMedical dossierMedical, Set<RDV> rdvs, Set<Ordonnance> ordonnances, Set<Medicament> medicaments, List<Consultation> consultations) {
        this.idUser = idUser;
        this.nom = nom;
        this.username = username;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.rolee = rolee;
        this.verifyMedecin = verifyMedecin;
        this.password = password;
        this.resetToken = resetToken;
        this.photo = photo;
        this.photoUrl = photoUrl;
        this.photoname = photoname;
        this.date_naissance = date_naissance;
        this.numero = numero;
        this.roles = roles;
        this.userRoleMed = userRoleMed;
        this.userRolePharm = userRolePharm;
        this.messages = messages;
        this.evaluations = evaluations;
        this.profile = profile;
        this.dossierMedical = dossierMedical;
        this.rdvs = rdvs;
        this.ordonnances = ordonnances;
        this.medicaments = medicaments;
        this.consultations = consultations;
    }
}


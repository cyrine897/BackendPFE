package com.example.sahtyapp1.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "DossierMedical")
@Builder

public class DossierMedical implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_doss")
    Long idDossier;
    @Lob
    @Column(name = "dossier_medical", columnDefinition = "LONGBLOB")
    private byte[] dossierMedical;
    private  String description;

    private String fileName;

    private  String type;

    private  String name;

    private  String nomUrgence;

    private  String numeroUrgence;

    private  Integer taille;

    private  Integer poids;

    private  String anticedentsMedicaments;

    private String vaccination;

    private Date dateVaccination;

    private String filePath;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ActivitePhysique> activitePhysique;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Consommation> consommation;

    @OneToOne
    @JoinColumn(name = "id_user" )
    @JsonBackReference
    private Utilisateur utilisateur;



}

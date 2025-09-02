package com.example.sahtyapp1.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RDV implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idRDV;
    String notif;
    String address;
    @Lob
    private byte[] DossierMed;

@Enumerated(EnumType.STRING)
    Specialite specialite;
@Enumerated(EnumType.STRING)
    Statut statut;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur utilisateur;

    @OneToOne
    @JoinColumn(name = "disponibilite_id") // Assurez-vous que le nom correspond à la colonne de la clé étrangère
    private Disponibilite disponibilite;
}


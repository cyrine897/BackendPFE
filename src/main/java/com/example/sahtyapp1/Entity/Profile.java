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
@Table(name = "profile")
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProfile;
    String nom;
    String prenom ;
    @Lob
    private byte[] DossierMed;
    @Lob
    private byte[] profilePicture;
    @OneToOne(mappedBy = "profile")
    private Utilisateur utilisateur;


}


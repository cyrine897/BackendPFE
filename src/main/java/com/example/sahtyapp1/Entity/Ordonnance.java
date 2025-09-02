package com.example.sahtyapp1.Entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Ordonnance")

public class Ordonnance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idOrd;
   Date date;
    String instructions;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur utilisateur;
    @ManyToMany(mappedBy = "ordonnances")
    private Set<Medicament> medicaments;
}


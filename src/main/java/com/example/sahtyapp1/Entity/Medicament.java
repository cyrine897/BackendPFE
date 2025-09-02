package com.example.sahtyapp1.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Medicament")

public class Medicament implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMed;
    String nom;
    String description;
    float prix ;
    @ManyToMany
    @JoinTable(
            name = "ordonnance_medicament",
            joinColumns = @JoinColumn(name = "medicament_id"),
            inverseJoinColumns = @JoinColumn(name = "ordonnance_id")
    )
    private Set<Ordonnance> ordonnances;
}

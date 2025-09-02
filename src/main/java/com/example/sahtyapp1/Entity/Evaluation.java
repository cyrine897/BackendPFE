package com.example.sahtyapp1.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Evaluation")

public class Evaluation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEval;
    String note;
    String commentaire;
    Date date;
    @ManyToMany(mappedBy = "evaluations")
    private Set<Utilisateur> utilisateurs = new HashSet<>();


}

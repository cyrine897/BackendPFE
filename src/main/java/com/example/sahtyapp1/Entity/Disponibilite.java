package com.example.sahtyapp1.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Disponibilite")

public class Disponibilite  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idDispo;

    Date date;
    @Enumerated(EnumType.STRING)
    Statut statut;
    @OneToOne(mappedBy = "disponibilite")
    private RDV rdv;
}

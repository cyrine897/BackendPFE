package com.example.sahtyapp1.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Consultation")
public class Consultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idConsulation;

    private LocalDate date;
    private String type;
    private String details;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private Utilisateur utilisateur;
}

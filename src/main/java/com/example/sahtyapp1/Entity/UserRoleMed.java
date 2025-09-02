package com.example.sahtyapp1.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorValue("MEDECIN")
@Table(name = "UserRoleMed")
public class UserRoleMed  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rolemed")
    private Long idRoleMed;

    @OneToOne(mappedBy = "userRoleMed")
    private Utilisateur utilisateur;
    @NotNull
    @Column(name = "piece_identite_med") // Ensure this matches the actual column name in the database
    private Long  pieceIdentiteMed;
    @NotNull

    @Lob
    @Column(name = "copiedelalicencedeMed")
    private byte[] copiedelalicencedeMed;


    @Lob
    @Column(name = "diplomes_med")
    private byte[] diplomesMed;
    @NotNull


    private Date dateObtentionDeLaLicenceMed ;
    @NotNull

    private Long numerodelicencedeMed ;
    @NotNull

    private String   qualificationsMed;
    @NotNull

    private String  positionMed;
    @NotNull

    private Long   numeroTelephoneMed;
    @NotNull

    private String adresseMed;
    @NotNull

    private String  nomMed;

    public Long getIdRoleMed() {
        return idRoleMed;
    }

    public void setIdRoleMed(Long idRoleMed) {
        this.idRoleMed = idRoleMed;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getPieceIdentiteMed() {
        return pieceIdentiteMed;
    }

    public void setPieceIdentiteMed(Long pieceIdentiteMed) {
        this.pieceIdentiteMed = pieceIdentiteMed;
    }

    public byte[] getCopiedelalicencedeMed() {
        return copiedelalicencedeMed;
    }

    public void setCopiedelalicencedeMed(byte[] copiedelalicencedeMed) {
        this.copiedelalicencedeMed = copiedelalicencedeMed;
    }

    public byte[] getDiplomesMed() {
        return diplomesMed;
    }

    public void setDiplomesMed(byte[] diplomesMed) {
        this.diplomesMed = diplomesMed;
    }

    public Date getDateObtentionDeLaLicenceMed() {
        return dateObtentionDeLaLicenceMed;
    }

    public void setDateObtentionDeLaLicenceMed(Date dateObtentionDeLaLicenceMed) {
        this.dateObtentionDeLaLicenceMed = dateObtentionDeLaLicenceMed;
    }

    public Long getNumerodelicencedeMed() {
        return numerodelicencedeMed;
    }

    public void setNumerodelicencedeMed(Long numerodelicencedeMed) {
        this.numerodelicencedeMed = numerodelicencedeMed;
    }

    public String getQualificationsMed() {
        return qualificationsMed;
    }

    public void setQualificationsMed(String qualificationsMed) {
        this.qualificationsMed = qualificationsMed;
    }

    public String getPositionMed() {
        return positionMed;
    }

    public void setPositionMed(String positionMed) {
        this.positionMed = positionMed;
    }

    public Long getNumeroTelephoneMed() {
        return numeroTelephoneMed;
    }

    public void setNumeroTelephoneMed(Long numeroTelephoneMed) {
        this.numeroTelephoneMed = numeroTelephoneMed;
    }

    public String getAdresseMed() {
        return adresseMed;
    }

    public void setAdresseMed(String adresseMed) {
        this.adresseMed = adresseMed;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }


}

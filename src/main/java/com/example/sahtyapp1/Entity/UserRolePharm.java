package com.example.sahtyapp1.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Pharmacien")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "UserRolePharm")

public class UserRolePharm  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rolepharm")
    private Long idRolePharm;

    @OneToOne(mappedBy = "userRolePharm")
    private Utilisateur utilisateur;

    private Long  pieceIdentitPharm ;

    @Lob
    @Column(name = "CopiedelalicencedePharm", columnDefinition = "LONGBLOB")
    private byte[] copiedelalicencedePharm;
    @Lob
    @Column(name = "DiplômesPharm", columnDefinition = "LONGBLOB")
    private byte[] diplomesPharm;


    private Date dateObtentionDeLaLicencePharm ;
    private Long numerodelicencedePharm ;
    private String   qualificationsPharm;
    private String  positionPharm;

    private Long   numeroTelephonePharm;
    private String adressePharm;
    private String  nomPharm;
    private Long  numerodelicencePharm ;

    public Long getIdRolePharm() {
        return idRolePharm;
    }

    public void setIdRolePharm(Long idRolePharm) {
        this.idRolePharm = idRolePharm;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Long getPieceIdentitPharm() {
        return pieceIdentitPharm;
    }

    public void setPieceIdentitPharm(Long pieceIdentitPharm) {
        this.pieceIdentitPharm = pieceIdentitPharm;
    }

    public byte[] getCopiedelalicencedePharm() {
        return copiedelalicencedePharm;
    }

    public void setCopiedelalicencedePharm(byte[] copiedelalicencedePharm) {
        this.copiedelalicencedePharm = copiedelalicencedePharm;
    }

    public byte[] getDiplomesPharm() {
        return diplomesPharm;
    }

    public void setDiplomesPharm(byte[] diplomesPharm) {
        this.diplomesPharm = diplomesPharm;
    }

    public Date getDateObtentionDeLaLicencePharm() {
        return dateObtentionDeLaLicencePharm;
    }

    public void setDateObtentionDeLaLicencePharm(Date dateObtentionDeLaLicencePharm) {
        this.dateObtentionDeLaLicencePharm = dateObtentionDeLaLicencePharm;
    }

    public Long getNumerodelicencedePharm() {
        return numerodelicencedePharm;
    }

    public void setNumerodelicencedePharm(Long numerodelicencedePharm) {
        this.numerodelicencedePharm = numerodelicencedePharm;
    }

    public String getQualificationsPharm() {
        return qualificationsPharm;
    }

    public void setQualificationsPharm(String qualificationsPharm) {
        this.qualificationsPharm = qualificationsPharm;
    }

    public String getPositionPharm() {
        return positionPharm;
    }

    public void setPositionPharm(String positionPharm) {
        this.positionPharm = positionPharm;
    }

    public Long getNumeroTelephonePharm() {
        return numeroTelephonePharm;
    }

    public void setNumeroTelephonePharm(Long numeroTelephonePharm) {
        this.numeroTelephonePharm = numeroTelephonePharm;
    }

    public String getAdressePharm() {
        return adressePharm;
    }

    public void setAdressePharm(String adressePharm) {
        this.adressePharm = adressePharm;
    }

    public String getNomPharm() {
        return nomPharm;
    }

    public void setNomPharm(String nomPharm) {
        this.nomPharm = nomPharm;
    }

    public Long getNumerodelicencePharm() {
        return numerodelicencePharm;
    }

    public void setNumerodelicencePharm(Long numerodelicencePharm) {
        this.numerodelicencePharm = numerodelicencePharm;
    }
}

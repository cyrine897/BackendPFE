package com.example.sahtyapp1.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class SignUpRequest {

    private String username;
    private String email;
    private String password;

    private String prenom;

    private String nom;


    private String adresse;
    private String rolee ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date_naissance;

    private Boolean verified = false; // Add a default false value for verification

    private Long numero;

    // Fields related to MEDECIN
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

    private Long  pieceIdentiteMed;

    @Lob
    @Column(name = "copiedelalicencedeMed")
    private byte[] copiedelalicencedeMed;


    @Lob
    @Column(name = "diplomes_med")
    private byte[] diplomesMed;

    private Date dateObtentionDeLaLicenceMed ;
    private Long numerodelicencedeMed ;
    private String   qualificationsMed;
    private String  positionMed;

    private Long   numeroTelephoneMed;
    private String adresseMed;
    private String  nomMed;

    public SignUpRequest(String username, String email, String password, String prenom, String nom, String adresse,
                         String rolee, LocalDate date_naissance, Boolean verified, Long numero,
                         Long pieceIdentitPharm, byte[] copiedelalicencedePharm, byte[] diplomesPharm,
                         Date dateObtentionDeLaLicencePharm, Long numerodelicencedePharm, String qualificationsPharm,
                         String positionPharm, Long numeroTelephonePharm, String adressePharm, String nomPharm,
                         Long numerodelicencePharm, Long pieceIdentiteMed, byte[] copiedelalicencedeMed,
                         byte[] diplomesMed, Date dateObtentionDeLaLicenceMed, Long numerodelicencedeMed,
                         String qualificationsMed, String positionMed, Long numeroTelephoneMed, String adresseMed,
                         String nomMed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.rolee = rolee;
        this.date_naissance = date_naissance;
        this.verified = verified;
        this.numero = numero;
        this.pieceIdentitPharm = pieceIdentitPharm;
        this.copiedelalicencedePharm = copiedelalicencedePharm;
        this.diplomesPharm = diplomesPharm;
        this.dateObtentionDeLaLicencePharm = dateObtentionDeLaLicencePharm;
        this.numerodelicencedePharm = numerodelicencedePharm;
        this.qualificationsPharm = qualificationsPharm;
        this.positionPharm = positionPharm;
        this.numeroTelephonePharm = numeroTelephonePharm;
        this.adressePharm = adressePharm;
        this.nomPharm = nomPharm;
        this.numerodelicencePharm = numerodelicencePharm;
        this.pieceIdentiteMed = pieceIdentiteMed;
        this.copiedelalicencedeMed = copiedelalicencedeMed;
        this.diplomesMed = diplomesMed;
        this.dateObtentionDeLaLicenceMed = dateObtentionDeLaLicenceMed;
        this.numerodelicencedeMed = numerodelicencedeMed;
        this.qualificationsMed = qualificationsMed;
        this.positionMed = positionMed;
        this.numeroTelephoneMed = numeroTelephoneMed;
        this.adresseMed = adresseMed;
        this.nomMed = nomMed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRolee() {
        return rolee;
    }

    public void setRolee(String rolee) {
        this.rolee = rolee;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
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

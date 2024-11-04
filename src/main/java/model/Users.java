package model;/*Classe abstraite regroupant l'ensemble des attributs communs a tous les utilisateurs*/
import java.util.*;
public class Users {
    public enum TypeUser {VOLUNTEER,BENEFICIARY,HEALTHPRO,ADMIN}
    /************Attributs*************/
    private String prenom;
    private String nom;
    private Date dateNaissance;
    private String mail;
    private String numTelephone;
    private TypeUser type;

    /***********Constructeur**********/
    public Users(String prenom, String nom, Date dateNaissance, String mail, String numTelephone, TypeUser type) {
        this.prenom = prenom;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.mail = mail;
        this.numTelephone = numTelephone;
        this.type = type;
    }

    /***********Methodes**************/
    //1// Les guetteurs
    public String getPrenom() {
        return prenom;
    }
    public String getNom() {
        return nom;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public String getMail() {
        return mail;
    }
    public String getNumTelephone() {
        return numTelephone;
    }
    public TypeUser getType() {
        return type;
    }

    //2// Les setteurs
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setNumTelephone(String numTelephone) {
        this.numTelephone = numTelephone;
    }
    public void setType(TypeUser type) {
        this.type = type;
    }
}

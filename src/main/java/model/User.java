package model;/*Classe abstraite regroupant l'ensemble des attributs communs a tous les utilisateurs*/

import java.sql.SQLException;

public class User {
    public void createMission() throws SQLException {
    }


    public enum TypeUser {VOLUNTEER,BENEFICIARY,HEALTHPRO,ADMIN}
    /************Attributs*************/
    private String firstname;
    private String name;
    private String birthDate;
    private String mail;
    private String phoneNumber;
    private TypeUser type;

    /***********Constructeur**********/
    public User(String prenom, String nom, String dateNaissance, String mail, String numTelephone, TypeUser type) {
        this.firstname = prenom;
        this.name = nom;
        this.birthDate = dateNaissance;
        this.mail = mail;
        this.phoneNumber = numTelephone;
        this.type = type;
    }
    public User(String prenom, String nom, String dateNaissance, String mail, String numTelephone) {
        this.firstname = prenom;
        this.name = nom;
        this.birthDate = dateNaissance;
        this.mail = mail;
        this.phoneNumber = numTelephone;
    }



    /***********Methodes**************/
    //1// Les getteurs

    public String getFirstname() {
        return firstname;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return String.valueOf(type);
    }


    //2// Les setteurs
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(TypeUser type) {
        this.type = type;
    }
}

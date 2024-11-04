package model;

import model.Users;

import java.util.Date;

public class Volunteers extends Users {

    /***********Constructeur
     * @param prenom
     * @param nom
     * @param dateNaissance
     * @param mail
     * @param numTelephone
     * @param type**********/
    public Volunteers(String prenom, String nom, Date dateNaissance, String mail, String numTelephone, TypeUser type) {
        super(prenom, nom, dateNaissance, mail, numTelephone, type);
    }
}

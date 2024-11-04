package model;

import java.util.Date;

/*Classe destinée a la gestion des utilisateurs qui demandent de l'aide */
public class Beneficiary extends Users {

    /***********Constructeur
     * @param prenom
     * @param nom
     * @param dateNaissance
     * @param mail
     * @param numTelephone
     * @param type**********/
    public Beneficiary(String prenom, String nom, Date dateNaissance, String mail, String numTelephone, TypeUser type) {
        super(prenom, nom, dateNaissance, mail, numTelephone, type);
    }
}
package model;


/*Classe destinée a la gestion des utilisateurs qui demandent de l'aide */
public class Beneficiary extends User {

    /***********Constructeur
     * @param firstName
     * @param name
     * @param birthDate
     * @param mail
     * @param phoneNumber
     * @param type**********/
    public Beneficiary(String firstName, String name, String birthDate, String mail, String phoneNumber, TypeUser type) {
        super(firstName, name, birthDate, mail, phoneNumber, type);
    }


}
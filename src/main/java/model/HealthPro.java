package model;


/*Classe destinée a la gestion des utilisateurs qui supervisent les bénéficiaires qui demandent de l'aide */
public class HealthPro extends User {
    /***********Constructeur
     * @param prenom
     * @param nom
     * @param dateNaissance
     * @param mail
     * @param numTelephone
     * @param type**********/
    public HealthPro(String prenom, String nom, String dateNaissance, String mail, String numTelephone, TypeUser type) {
        super(prenom, nom, dateNaissance, mail, numTelephone, type);
    }
}
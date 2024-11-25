package model;

public class Volunteer extends User {

    /***********Constructeur
     * @param prenom
     * @param nom
     * @param dateNaissance
     * @param mail
     * @param numTelephone
     * @param type**********/
    public Volunteer(String prenom, String nom, String dateNaissance, String mail, String numTelephone, TypeUser type) {
        super(prenom, nom, dateNaissance, mail, numTelephone, type);
    }

    //Add function AcceptMission (id mission) -> transform state -> 0
    //ajouter la mission a la liste de missions du volunteer

    //Function print
    //current missions :........
    //previous misisons : ........
}

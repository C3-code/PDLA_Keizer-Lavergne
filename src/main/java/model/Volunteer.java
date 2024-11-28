package model;

import controller.GestionBdd;

import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

    private List<Mission> currentMissions;
    private List<Mission> previousMissions;
    //Add function AcceptMission (id mission) -> transform state -> 0
    //ajouter la mission a la liste de missions du volunteer
    public void acceptMission(int idMission) throws SQLException {
        GestionBdd.getInstance().updateState(idMission, this.getName());
        System.out.println("La mission a bien été acceptée");
    }

    public void printCurrentMission (){

    }
    //Fonction print current mission -> parcourt la base de donnée et affiche les missions state=0, volounteer = ce volunteer

    //Function print
    //current missions :........
    //previous misisons : ........
}

package model;

import controller.GestionBdd;

import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static controller.MainProgram.base;

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
    /*public void acceptMission(int idMission) throws SQLException {
        base.getMissionFromId(idMission).updateVolunteer(this.getMail());
        GestionBdd.getInstance().participateInMission(idMission, this.getMail());
        System.out.println("La mission a bien été acceptée");
    }*/

    /*public void printCurrentMission () throws SQLException {
        GestionBdd.getInstance().getCurrentMissions(this);
    }*/
    //Fonction print current mission -> parcourt la base de donnée et affiche les missions state=0, volounteer = ce volunteer

    //Function print
    //current missions :........
    //previous misisons : ........
}

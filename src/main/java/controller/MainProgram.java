package controller;

import view.WelcomeView;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;

public class MainProgram {

    //Se connecter à la base de données

    public static GestionBdd base = GestionBdd.getInstance();

    static UserConnection userConnection;



    public static void main(String[] args) throws SQLException, ParseException {
        //System.out.println("Hello world!");
        /*********************/
        /**Connexion a la BDD*/
        /*********************/

        base.Connexion_BDD();
        //inscription = new Inscription();
        //inscription.userCreation();
        //base.printMissions();

        // Lancer l'application dans le thread de l'interface graphique
        SwingUtilities.invokeLater(WelcomeView::createWelcomeView);


        //Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
    }
}
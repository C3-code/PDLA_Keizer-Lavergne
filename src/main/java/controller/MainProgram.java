package controller;

import model.GestionBdd;
import view.WelcomeView;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;

public class MainProgram {

    public static GestionBdd base = GestionBdd.getInstance();
    static UserConnection userConnection;

    public static void main(String[] args) throws SQLException, ParseException {

        /*  Connexion a la BDD   */
        base.Connexion_BDD();

        /* Lancer l'application dans le thread de l'interface graphique*/
        SwingUtilities.invokeLater(WelcomeView::showWelcomeView);

    }
}
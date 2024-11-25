package controller;

import controller.GestionBdd;
import model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class MainProgram {

    //Se connecter à la base de données

    public static GestionBdd base = new GestionBdd();

    static Inscription inscription;



    public static void main(String[] args) throws SQLException, ParseException {
        //System.out.println("Hello world!");
        /*********************/
        /**Connexion a la BDD*/
        /*********************/

        base.Connexion_BDD();
        inscription = new Inscription();
        inscription.userCreation();
        base.printMissions();




        //Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
    }
}
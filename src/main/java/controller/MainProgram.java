package controller;

import controller.GestionBdd;

import java.sql.*;
import java.util.Scanner;

public class MainProgram {

    //Se connecter à la base de données
  




    public static void main(String[] args) throws SQLException {
        //System.out.println("Hello world!");

        /*********************/
        /**A deplacer eventuellement*/
        /*********************/
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your last name ?");
        String name = sc.nextLine();
        System.out.println("What is your first name ?");
        String firstName = sc.nextLine();
        sc.close();
        System.out.println("You chose :"+name+firstName);
        //On crée un utilisateur a partir des infos qu'on recupere puis on passe l'utilisateur en parametre de la fonction addperson

        /*********************/
        /**Connexion a la BDD*/
        /*********************/
        GestionBdd base = new GestionBdd();
        base.Connexion_BDD();
        base.addPerson();


        /****Creer ligne dans BDD*/


        System.out.println("Hello");
    }
}
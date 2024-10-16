package org.example;

import java.sql.*;

public class Main {

    //Se connecter à la base de données
    public static void Connexion_BDD() {
        //a implémenter
        String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020 ";
        String userName = "projet_gei_020";
        String password = "ih9sieT5";

        Connection conn = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);

            Statement statement = conn.createStatement();
            //statement.execute("CREATE TABLE test(id INTEGER, nom VARCHAR(100))");

            //statement.execute("insert into test(id,nom) values(1,'Lelia'),(2,'Celia')");

            ResultSet resultSet = statement.executeQuery("select * from test");
            //conn.close();
        } catch (SQLException e) {
            System.err.println("Driver non chargé !");
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        //System.out.println("Hello world!");

        /*********************/
        /**Connexion a la BDD*/
        /*********************/
        Connexion_BDD();

        /****Creer ligne dans BDD*/

        System.out.println("Hello");
    }
}
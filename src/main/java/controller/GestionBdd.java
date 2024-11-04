package controller;

import java.sql.*;

/*Classe destinée a gerer la connexion de l'application a la base de données*/
public class GestionBdd{
    /*********Constantes**********/
    String link = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020 ";
    String userName = "projet_gei_020";
    String pwd = "ih9sieT5";

    private String url;
    private String projectName;
    private String password;

    private Connection conn = null;

    /************Constructeur*******/
    public GestionBdd() {
        this.url = link;
        this.projectName = userName;
        this.password = pwd;
    }

    /************Methode*******/
    public void Connexion_BDD() {

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.url, this.projectName, this.password);


            //conn.close();
        } catch (SQLException e) {
            System.err.println("Driver non chargé !");
            e.printStackTrace();
        }

    }

    public void addPerson() throws SQLException {

        Statement statement = this.conn.createStatement();

        statement.executeUpdate("INSERT INTO Individus(nom,prenom,mail,numeroTelephone,dateNaissance,typeUser)" + "VALUES('Lavergne','Lelia','clelia@ahhh.com','0355123365','11/01/2022','Beneficiary')");

    }
}

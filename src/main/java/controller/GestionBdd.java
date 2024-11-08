package controller;

import model.Beneficiary;
import model.Mission;
import model.User;
import model.User;

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

    public void addPerson(User user) throws SQLException {

        //Statement statement = this.conn.createStatement();

        String query = "INSERT INTO Individus(nom, prenom, mail, numeroTelephone, dateNaissance, typeUser) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Remplir les paramètres de la requête avec les valeurs de l'utilisateur
            statement.setString(1, user.getName());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getMail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getBirthDate());
            statement.setString(6, user.getType());

            // Exécution de la requête
            statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw e;  // Propager l'exception après l'avoir affichée
            }
    }
    public void addMission(String name, Mission mission) throws SQLException {

        //Statement statement = this.conn.createStatement();

        String query = "INSERT INTO Missions(beneficiary,missionName,description,expirationDate,location,healthPro) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Remplir les paramètres de la requête avec les valeurs de l'utilisateur
            statement.setString(1, name);
            statement.setString(2, mission.getMissionName());
            statement.setString(3, mission.getDescription());
            statement.setString(4, mission.getDate());
            statement.setString(5, mission.getLocation());
            statement.setString(6, mission.getHealthPro());

            // Exécution de la requête
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

        //statement.executeUpdate("INSERT INTO Individus(nom,prenom,mail,numeroTelephone,dateNaissance,typeUser)" + "VALUES(" + user.getName() + "," + user.getFirstname() + "," + user.getMail() + "," + user.getPhoneNumber() + "," + user.getBirthDate() + ", 'Beneficiary')");

}


package controller;

import model.Mission;
import model.User;

import java.sql.*;

/*Classe destinée a gerer la connexion de l'application a la base de données*/
public class GestionBdd{

    private static final GestionBdd BDD = new GestionBdd();
    public static GestionBdd getInstance() {
        return BDD;
    }
    /*********Constantes**********/
    String link = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020 ";
    String userName = "projet_gei_020";
    String pwd = "ih9sieT5";

    private String url;
    private String projectName;
    private String password;
    private Connection conn = null;
    private String query;

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

        query = "INSERT INTO Individus(nom, prenom, mail, numeroTelephone, dateNaissance, typeUser) VALUES (?, ?, ?, ?, ?, ?)";

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

        query = "INSERT INTO Missions(beneficiary,missionName,description,expirationDate,location,healthPro,state) VALUES (?, ?, ?, ?, ?, ?,true)";

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

    /** Modifier l'état de la mission lorsuq'elle a été acceptée
     * @param numMission
     * @throws SQLException
     */
    public void updateState(int numMission, String volunteerName) throws SQLException {
        Statement statement = this.conn.createStatement();
        statement.executeQuery("UPDATE Missions SET state = 0 WHERE (id = numMission);");
        statement.executeQuery("UPDATE Missions SET volunteer = volunteerName WHERE (id = numMission);");
    }

    /** Display the table of current open missions
     *
     * @throws SQLException
     */
    public void printMissions() throws SQLException {
        Statement statement = this.conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Missions WHERE (state = 1);"); //only print missions that are open (state = true = 1)
        while(result.next()) {
            final int id = result.getInt("id");
            final String beneficiary = result.getString("beneficiary");
            final String mission = result.getString("missionName");
            final String description = result.getString("description");
            final String date = result.getString("expirationDate");
            final String location = result.getString("location");
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.println("Mission " +id +" : "+ mission );
            System.out.println("            "+ "inquirer: "+beneficiary+ "   -   date: "+date+"   -   location: "+location);
            System.out.println("            Description: "+ description);
        }
    }

    public boolean userExists(String email) throws SQLException {
        String query = "SELECT * FROM Individus WHERE mail = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1, email);  // Set the email as a parameter
            ResultSet result = stmt.executeQuery();

            if (result.next()) {  // Check if there is a row
                return true;
            } else {
                return false;
            }
        }
    }

        //statement.executeUpdate("INSERT INTO Individus(nom,prenom,mail,numeroTelephone,dateNaissance,typeUser)" + "VALUES(" + user.getName() + "," + user.getFirstname() + "," + user.getMail() + "," + user.getPhoneNumber() + "," + user.getBirthDate() + ", 'Beneficiary')");

}


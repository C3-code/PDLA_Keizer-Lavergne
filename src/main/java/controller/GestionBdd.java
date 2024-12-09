package controller;

import model.Avis;
import model.Mission;
import model.User;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

import static controller.UserConnection.thisUser;


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

    public void getProfile(String mail) throws SQLException {

        //par la suite faire un if pour differencier getcurrent missions d'un volontaire et d'un beneficiare
        // Utilisation d'un PreparedStatement pour la requête avec paramètre
        String query = "SELECT nom,prenom,numeroTelephone,dateNaissance,typeUser FROM Individus WHERE (mail = ? );";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {

            // Lier le paramètre (l'email de l'utilisateur)
            statement.setString(1, mail);

            /* Construction du catalogue des missions depuis les informations de la base de données */
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                String numeroTelephone = result.getString("numeroTelephone");
                String dateNaissance = result.getString("dateNaissance");
                String typeUser = result.getString("typeUser");
                UserConnection.setUpUser(prenom,nom,dateNaissance,mail,numeroTelephone,typeUser);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void addMission(String name, Mission mission) throws SQLException {

        //Statement statement = this.conn.createStatement();

        query = "INSERT INTO Missions(beneficiary,missionName,description,expirationDate,location,healthPro,state,volunteer) VALUES (?, ?, ?, ?, ?,?,?,?)";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Remplir les paramètres de la requête avec les valeurs de l'utilisateur
            statement.setString(1, name);
            statement.setString(2, mission.getMissionName());
            statement.setString(3, mission.getDescription());
            statement.setString(4, mission.getDate());
            statement.setString(5, mission.getLocation());
            statement.setString(6, mission.getHealthPro());
            statement.setString(7, "open");
            statement.setString(8, "none" );

            // Exécution de la requête
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

    /** Display the table of current open missions
     *
     * @throws SQLException
     */
    public String getOpenMissions() throws SQLException {
        Statement statement = this.conn.createStatement();
        ResultSet result;
        String query;

        // Vérification du type d'utilisateur (Bénéficiaire ou Volontaire)
        if (thisUser.getType().equals("BENEFICIARY")) {
            // Si c'est un bénéficiaire, on filtre les missions "open" et où le nom du bénéficiaire est égal à celui de l'utilisateur
            query = "SELECT * FROM Missions WHERE (state = 'open') AND (beneficiary = ?);";

            // Utilisation d'un PreparedStatement pour passer le paramètre
            PreparedStatement preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, thisUser.getName());

            // Exécution de la requête
            result = preparedStatement.executeQuery();
        } else {
            // Si c'est un volontaire, on affiche toutes les missions "open"
            query = "SELECT * FROM Missions WHERE (state = 'open');";

            // Exécution de la requête sans paramètre
            result = statement.executeQuery(query);
        }

        String texte = "----------------------------------------------- Catalog of available missions ------------------------------------------------------------\n";
        while(result.next()) {
            final int id = result.getInt("id");
            final String beneficiary = result.getString("beneficiary");
            final String mission = result.getString("missionName");
            final String description = result.getString("description");
            final String date = result.getString("expirationDate");
            final String location = result.getString("location");
            texte = texte + "-----------------------------------------------------------------------------------------------------------------------------------------------\n"
                            + "  Mission " +id +" : "+ mission + "\n"
                            +"                    "+ "inquirer: "+beneficiary+ "   -   date: "+date+"   -   location: "+location + "\n"
                            +"                    Description: "+ description +"\n";
        }
        return texte;
    }

    public String getCurrentMissions() throws SQLException {
        String allMissions = null;

        // Utilisation d'un PreparedStatement, quel que soit le type d'utilisateur
        PreparedStatement preparedStatement;
        ResultSet result;
        String query;

        // Vérification du type d'utilisateur (Bénéficiaire ou Volontaire)
        if (thisUser.getType().equals("BENEFICIARY")) {
            // Si c'est un bénéficiaire, on filtre les missions "accepted" et où le nom du bénéficiaire est égal à celui de l'utilisateur
            query = "SELECT * FROM Missions WHERE (state = 'accepted') AND (beneficiary = ?);";

            // Création d'un PreparedStatement
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, thisUser.getName());

            // Exécution de la requête
        } else {
            // Si c'est un volontaire, on filtre les missions "accepted" et où le mail du volontaire est égal à celui de l'utilisateur
            query = "SELECT * FROM Missions WHERE (state = 'accepted') AND (volunteer = ?);";

            // Création d'un PreparedStatement
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, thisUser.getMail());

            // Exécution de la requête
        }
        result = preparedStatement.executeQuery();

        allMissions = "--------------------------------------------------- My current missions ------------------------------------------------------------\n";
            while (result.next()) {
                final int id = result.getInt("id");
                final String beneficiary = result.getString("beneficiary");
                final String mission = result.getString("missionName");
                final String description = result.getString("description");
                final String date = result.getString("expirationDate");
                final String location = result.getString("location");

                allMissions += "-----------------------------------------------------------------------------------------------------------------------------------------------\n"
                        + "  Mission " + id + " : " + mission + "\n"
                        + "                    " + "inquirer: " + beneficiary + "   -   date: " + date + "   -   location: " + location + "\n"
                        + "                    Description: " + description + "\n";
            }

        return allMissions;
    }

    public String getPreviousMissions(User user) throws SQLException {
        String previousMissions = null;
        PreparedStatement preparedStatement;
        ResultSet result;
        String query;

        // Vérification du type d'utilisateur (Bénéficiaire ou Volontaire)
        if (thisUser.getType().equals("BENEFICIARY")) {
            // Si c'est un bénéficiaire, on filtre les missions "accepted" et où le nom du bénéficiaire est égal à celui de l'utilisateur
            query = "SELECT * FROM Missions WHERE (state = 'done') AND (beneficiary = ?);";

            // Création d'un PreparedStatement
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, thisUser.getName());

            // Exécution de la requête
        } else {
            // Si c'est un volontaire, on filtre les missions "accepted" et où le mail du volontaire est égal à celui de l'utilisateur
            query = "SELECT * FROM Missions WHERE (state = 'done') AND (volunteer = ?);";

            // Création d'un PreparedStatement
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setString(1, thisUser.getMail());

            // Exécution de la requête
        }
        result = preparedStatement.executeQuery();

        previousMissions = "--------------------------------------------------- My previous missions ------------------------------------------------------------\n";
            while (result.next()) {
                final int id = result.getInt("id");
                final String beneficiary = result.getString("beneficiary");
                final String mission = result.getString("missionName");
                final String description = result.getString("description");
                final String date = result.getString("expirationDate");
                final String location = result.getString("location");

                previousMissions += "-----------------------------------------------------------------------------------------------------------------------------------------------\n"
                        + "  Mission " + id + " : " + mission + "\n"
                        + "                    " + "inquirer: " + beneficiary + "   -   date: " + date + "   -   location: " + location + "\n"
                        + "                    Description: " + description + "\n";
            }


        return previousMissions;
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

    public boolean availableMission(String number) throws SQLException {
        String query = "SELECT * FROM Missions WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1, number);  // Set the email as a parameter
            ResultSet result = stmt.executeQuery();
            // Check if there is a row
            return result.next();
        }
    }

    /** Modifier l'état de la mission lorsqu'elle a été acceptée
     * @param numMission
     * @throws SQLException
     */
    public void participateInMission(int numMission, String volunteerMail) throws SQLException {
        String query = "UPDATE Missions SET state = 'accepted', volunteer = ? WHERE id = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            // Lier les paramètres à la requête
            stmt.setString(1, volunteerMail); // Le nom du volontaire
            stmt.setInt(2, numMission);// L'ID de la mission

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

    public void endMission(int numMission) throws SQLException {
        String query = "UPDATE Missions SET state = 'done' WHERE id = ?;";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            // Lier les paramètres à la requête
            stmt.setInt(1, numMission); // Le nom du volontaire
            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

    public boolean missionExists(int id) throws SQLException {
        String query = "SELECT * FROM Missions WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setInt(1, id);  // Set the email as a parameter
            ResultSet result = stmt.executeQuery();

            if (result.next()) {  // Check if there is a row
                return true;
            } else {
                return false;
            }
        }
    }

    public void createCommentFromMissionId(String comment, int id) throws SQLException {

        //par la suite faire un if pour differencier getcurrent missions d'un volontaire et d'un beneficiare
        // Utilisation d'un PreparedStatement pour la requête avec paramètre
        String query = "SELECT * FROM Missions WHERE id = ? ;";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {

            // Lier le paramètre (l'email de l'utilisateur)
            statement.setInt(1, id);

            /* Construction du catalogue des missions depuis les informations de la base de données */
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String beneficiary = result.getString("beneficiary");
                String nomMission = result.getString("missionName");
                //String description = result.getString("description");
                String date = result.getString("expirationDate");
                //String location = result.getString("location");
                String volunteer = result.getString("volunteer");


                Avis avis;

                if (thisUser.getType().equals("BENEFICIARY")) {
                    avis = new Avis(nomMission, id, date, comment, volunteer);
                } else {
                    avis = new Avis(nomMission, id, date, comment, beneficiary);
                }

                MainProgram.base.addComment(avis);
            } else {
                throw new SQLException("Mission with id" + id + "not found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void addComment(Avis avis) throws SQLException {
        query = "INSERT INTO Avis(idMissionn, utilisateur,missionName,commentDate,destinataire,commentaire) VALUES (?, ?,?, ?, ?, ?)";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Remplir les paramètres de la requête avec les valeurs de l'utilisateur
            statement.setInt(1, avis.getMissionId());
            statement.setString(2, thisUser.getName());
            statement.setString(3, avis.getMissionName());
            statement.setString(4, avis.getCommentDate());
            statement.setString(5, avis.getDestinataire());
            statement.setString(6, avis.getCommentaire());

            // Exécution de la requête
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

    public String getComments() throws SQLException {
        ResultSet result;
        String query;

        query = "SELECT * FROM Avis WHERE (destinataire = ?);";
        PreparedStatement preparedStatement = this.conn.prepareStatement(query);
        preparedStatement.setString(1, thisUser.getName());

        // Exécution de la requête
        result = preparedStatement.executeQuery();


        String texte = "----------------------------------------------- Comments left ------------------------------------------------------------\n";
        while(result.next()) {
            final int id = result.getInt("idMissionn");
            final String userName = result.getString("utilisateur");
            final String mission = result.getString("missionName");
            final String commentaire = result.getString("commentaire");
            final String date = result.getString("commentDate");

            texte = texte + "-----------------------------------------------------------------------------------------------------------------------------------------------\n"
                    + "A comment was left for you regarding mission " +id +" : "+ mission + "\n"
                    +"       [" + userName + " wrote] : "+commentaire  + "\n"
                    +"       Date : " + date + "\n";
        }
        return texte;
    }





    //statement.executeUpdate("INSERT INTO Individus(nom,prenom,mail,numeroTelephone,dateNaissance,typeUser)" + "VALUES(" + user.getName() + "," + user.getFirstname() + "," + user.getMail() + "," + user.getPhoneNumber() + "," + user.getBirthDate() + ", 'Beneficiary')");

}


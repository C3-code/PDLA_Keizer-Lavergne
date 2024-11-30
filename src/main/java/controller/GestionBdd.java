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

    /** Display the table of current open missions
     *
     * @throws SQLException
     */
    public String getAllMissions() throws SQLException {
        Statement statement = this.conn.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM Missions WHERE (state = 'open');"); //only print missions that are open (state = true = 1)
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

    public String getCurrentMissions(User user) throws SQLException {
        String allMissions = null;
        //par la suite faire un if pour differencier getcurrent missions d'un volontaire et d'un beneficiare
        // Utilisation d'un PreparedStatement pour la requête avec paramètre
        String query = "SELECT * FROM Missions WHERE (state = 'accepted') AND (volunteer = ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Lier le paramètre (l'email de l'utilisateur)
            statement.setString(1, user.getMail());

            /* Construction du catalogue des missions depuis les informations de la base de données */
            ResultSet result = statement.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }

        return allMissions;
    }

    public String getPreviousMissions(User user) throws SQLException {
        String previousMissions = null;
        //par la suite faire un if pour differencier getcurrent missions d'un volontaire et d'un beneficiare
        // Utilisation d'un PreparedStatement pour la requête avec paramètre
        String query = "SELECT * FROM Missions WHERE (state = 'done') AND (volunteer = ?)";
        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, user.getMail());


            /* Construction du catalogue des missions depuis les informations de la base de données */
            ResultSet result = statement.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
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
        String query = "UPDATE Missions SET state = 'accepted', volunteer = ? WHERE id = ?";
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





    //statement.executeUpdate("INSERT INTO Individus(nom,prenom,mail,numeroTelephone,dateNaissance,typeUser)" + "VALUES(" + user.getName() + "," + user.getFirstname() + "," + user.getMail() + "," + user.getPhoneNumber() + "," + user.getBirthDate() + ", 'Beneficiary')");

}


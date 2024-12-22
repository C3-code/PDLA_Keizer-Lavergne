package model;

import controller.UserConnection;

import java.sql.*;

import static controller.UserConnection.thisUser;


/*Classe destinée à la gestion de la connexion de l'application à la base de données*/
/*Nota bene: après réflexion nous nous sommes rendues compte qu'il serait plus judicieux de répartir les méthodes de cette classe
* dans trois classes différentes (une pour chaque table présentent dans notre base de données, ie : Users, Missions, Avis), c'est un point d'amélioration */
public class GestionBdd{

    /*******Création d'un SINGLETON (design pattern) *******/
    private static final GestionBdd BDD = new GestionBdd();
    public static GestionBdd getInstance() { //instance rendue publique pour que les différentes classes du projet puissent y accéder
        return BDD;
    }

    /*********Constantes**********/
    String link = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020 ";
    String userName = "projet_gei_020";
    String pwd = "ih9sieT5";

    /*********Attributs**********/
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

    /************Methodes*******/
    public void Connexion_BDD() {

        try {
            this.conn = DriverManager.getConnection(this.url, this.projectName, this.password);
        } catch (SQLException e) {
            System.err.println("Driver non chargé !"); //gérer la levée d'erreur
            e.printStackTrace();
        }
    }

    public void addPerson(User user) throws SQLException {

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

        // Utilisation d'un PreparedStatement pour la requête avec paramètre
        String query = "SELECT nom,prenom,numeroTelephone,dateNaissance,typeUser FROM Individus WHERE (mail = ? );";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {

            // Lier le paramètre (l'email de l'utilisateur)
            statement.setString(1, mail);

            //Construction du catalogue des missions depuis les informations de la base de données
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

    public void addMission(String mail, Mission mission) throws SQLException {
        query = "INSERT INTO Missions(beneficiary,missionName,description,expirationDate,location,healthPro,state,volunteer) VALUES (?, ?, ?, ?, ?,?,?,?)";

        try (PreparedStatement statement = this.conn.prepareStatement(query)) {
            // Remplir les paramètres de la requête avec les valeurs de l'utilisateur
            statement.setString(1, mail);
            statement.setString(2, mission.getMissionName());
            statement.setString(3, mission.getDescription());
            statement.setString(4, mission.getDate());
            statement.setString(5, mission.getLocation());
            statement.setString(6, mission.getHealthPro());
            statement.setString(7, "open");
            statement.setString(8, mission.getVolunteer());

            // Exécution de la requête
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Propager l'exception après l'avoir affichée
        }
    }

    /**Affichage de la liste des missions courantes
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
            preparedStatement.setString(1, thisUser.getMail());

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
            preparedStatement.setString(1, thisUser.getMail());

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
            preparedStatement.setString(1, thisUser.getMail());

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

    public Mission getMissionFromId(int id) throws SQLException {
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
                String description = result.getString("description");
                String date = result.getString("expirationDate");
                String location = result.getString("location");
                String volunteer = result.getString("volunteer");
                Mission mission = new Mission(beneficiary,nomMission,description,date,location);
                mission.updateVolunteer(volunteer);

                return mission;

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
            statement.setString(2, thisUser.getMail());
            statement.setString(3, avis.getMissionName());
            statement.setString(4, avis.getCommentDate());
            statement.setString(5, avis.getDestinataire());
            statement.setString(6, avis.getComment());

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
        preparedStatement.setString(1, thisUser.getMail());

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
}


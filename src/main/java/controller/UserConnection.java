package controller;

import model.*;
import view.*;
import java.sql.SQLException;

public class UserConnection {
    public static User thisUser;
    public static User getInstance() {return thisUser;}

    /** Méthode permettant de basculer l'interface graphique sur la vue de création de compte
     */
    public static void showCreateAccountView() {
        CreateAccountView subscription = new CreateAccountView();
        subscription.showSubscriptionField();
    }

    /** Méthode permettant de créer u objet user à partir des informations remplies dans le formulaire d'inscription
     * (informations récupérées ici via les arguments de la méthode)
     * Selon le rôle souhaité par l'utilisateur une sous-classe différente de user est instanciée
     */
    public static User setUpUser(String firstName, String name, String birthDate, String mail, String phoneNumber, String role) throws SQLException {
        switch (role) {
            case "BENEFICIARY":
                thisUser = new Beneficiary(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.BENEFICIARY);
                break;
            case "VOLUNTEER":
                thisUser  = new Volunteer(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.VOLUNTEER);
                break;
            case "HEALTHPRO":
                thisUser  = new HealthPro(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.HEALTHPRO);
                break;
            default:
                throw new RuntimeException();
        }
        return thisUser;
    }

    /** Méthode utilisée pour afficher la vue de connexion dans le cas où l'utilisateur possède déjà un compte
     */
    public static void showConnectionView() {
        ConnectionView connection = new ConnectionView();
        connection.showConnectionField();
    }

    /** Méthode permettant la connexion de l'utilisateur à son compte
     * La vérification de l'existance du compte se fait à partir de l'adresse mail de l'utilisateur (considérée unique)
     * Dans le cas où aucun compte correspondant n'a été trouvé la tentative de connexion echoue
     * Dans le cas où le compte existe, toutes les informations sur l'utilisateur (historique de missions, missions en cours) sont chargées
     */
    public static void connectUser(String mail) throws SQLException {
        boolean exists = GestionBdd.getInstance().userExists(mail);
        if (!exists) {
            System.out.println("l'utilisateur n'existe pas");
        }
        else {
            System.out.println("Connection with email address: " + mail);
            GestionBdd.getInstance().getProfile(mail);// charger toutes les infos de l'utilisateur a partir de son adresse mail
            if (thisUser.getType().equals("VOLUNTEER")) {
                VolunteerView.showVolunteerView(); //affiche les données personnelles de l'utilisateur dans une fenêtre Volunteer
            }
            else if (thisUser.getType().equals("BENEFICIARY")) {
                System.out.println(thisUser.getType());
                BeneficiaryView.showBeneficiaryView(); //affiche les données personnelles de l'utilisateur dans une fenêtre Beneficiary
            }
        }

    }

}

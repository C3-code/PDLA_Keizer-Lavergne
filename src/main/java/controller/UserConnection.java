package controller;

import model.*;
import view.*;
import java.sql.SQLException;


public class UserConnection {
    public static User thisUser;
    public static User getInstance() {
        return thisUser;
    }

    // Cette méthode permet de basculer vers la vue de création de compte
    public static void showCreateAccountView() {
        CreateAccountView subscription = new CreateAccountView();
        subscription.showSubscriptionField();
    }


    public static void createUser(String firstName, String name, String birthDate, String mail, String phoneNumber, String role) throws SQLException {
            //Creation en local (notre instance de user)
            switch (role) {
                case "BENEFICIARY":
                    thisUser = new Beneficiary(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.BENEFICIARY);
                    //user.createMission();
                    break;
                case "VOLUNTEER":
                    thisUser  = new Volunteer(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.VOLUNTEER);
                    break;
                case "HEALTHPRO":
                    thisUser  = new HealthPro(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.HEALTHPRO);
                    break;
                case "ADMIN":
                    thisUser  = new Admin(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.ADMIN);
                    break;
                default:
                    System.out.println("Please rewrite your role, we didn't understand");
            }

            /***Add it in the database***/
            GestionBdd.getInstance().addPerson(thisUser);
            System.out.println("You are now registered as a new user !");
    }

    public static void setUpUser(String firstName, String name, String birthDate, String mail, String phoneNumber, String role) throws SQLException {
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
            case "ADMIN":
                thisUser  = new Admin(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.ADMIN);
                break;
        }
        System.out.println("Infos retrouvees: nom: "+ thisUser.getName()+" mail: "+thisUser.getMail());
    }

    public static void showConnectionView() {
        ConnectionView connection = new ConnectionView();
        connection.showConnectionField();
    }

    public static void connectUser(String mail) throws SQLException {
        boolean exists = GestionBdd.getInstance().userExists(mail);
        if (!exists) {
            System.out.println("l'utilisateur existe pas chacal");
        }
        else {
            System.out.println("Connection with email address: " + mail);
            //Appel de l'ui Profile adaptee au profil de l'utilisateur
            GestionBdd.getInstance().getProfile(mail);// charger toutes les infos de l'utilisateur a partir de son mail
            if (thisUser.getType().equals("VOLUNTEER")) {
                VolunteerView.showVolunteerView(); //cense afficher que  les current/previous mission de l'utilisateur actuel
            }
            else if (thisUser.getType().equals("BENEFICIARY")) {
                BeneficiaryView.showBeneficiaryView();
            }

        }

    }

}

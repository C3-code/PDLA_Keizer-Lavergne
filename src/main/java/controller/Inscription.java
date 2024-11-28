package controller;

import model.*;
import view.*;
import java.sql.SQLException;
import java.util.*;

import java.util.Scanner;

import static controller.MainProgram.base;

public class Inscription {
    public static User user;

    // Cette méthode permet de basculer vers la vue de création de compte
    public static void showCreateAccountView() {
        CreateAccountView subscription = new CreateAccountView();
        subscription.showSubscriptionField();
    }
    public static void createUser(String firstName, String name, String birthDate, String mail, String phoneNumber, String role) throws SQLException {


            switch (role) {
                case "BENEFICIARY":
                    user = new Beneficiary(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.BENEFICIARY);
                    //user.createMission();
                    break;
                case "VOLUNTEER":
                    user = new Volunteer(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.VOLUNTEER);
                    break;
                case "HEALTHPRO":
                    user = new HealthPro(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.HEALTHPRO);
                    break;
                case "ADMIN":
                    user = new Admin(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.ADMIN);
                    break;
                default:
                    System.out.println("Please rewrite your role, we didn't understand");
            }

            /***Add it in the database***/
            GestionBdd.getInstance().addPerson(user);
            System.out.println("You are now registered as a new user !");
    }

}

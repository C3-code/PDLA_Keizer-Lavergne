package controller;

import model.*;

import java.sql.SQLException;
import java.util.*;

import java.util.Scanner;

import static controller.MainProgram.base;

public class Inscription {
    public static User user;

    public void userCreation() throws SQLException {
        /*********************/
        /**A deplacer eventuellement*/
        /*********************/
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your last name ?");
        String name = sc.nextLine();
        System.out.println("What is your first name ?");
        String firstName = sc.nextLine();
        System.out.println("What is your birth date ? (XX/YY/ZZZZ)");
        String birthDate = sc.nextLine();
        System.out.println("What is your email address ?");
        String mail = sc.nextLine();
        System.out.println("What is your phone number ?");
        String phoneNumber = sc.nextLine();
        Boolean typeOk = false;
        while (!typeOk) {
            System.out.println("Do you want to connect as BENEFICIARY, VOLUNTEER, HEALTHPRO, ADMIN?");
            String type = sc.nextLine();
            switch (type) {
                case "BENEFICIARY":
                    user = new Beneficiary(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.BENEFICIARY);
                    user.createMission();
                    typeOk = true;
                    break;
                case "VOLUNTEER":
                    user = new Volunteer(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.VOLUNTEER);
                    typeOk = true;
                    break;
                case "HEALTHPRO":
                    user = new HealthPro(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.HEALTHPRO);
                    typeOk = true;
                    break;
                case "ADMIN":
                    user = new Admin(firstName, name, birthDate, mail, phoneNumber, User.TypeUser.ADMIN);
                    typeOk = true;
                    break;
                default:
                    System.out.println("Please rewrite your role, we didn't understand");
            }
        }
        sc.close();

        /***Add it in the database***/
        base.addPerson(user);



        System.out.println("You are now registered as a new user !");
    }
}

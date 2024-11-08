package model;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import static controller.MainProgram.base;

/*Classe destinée a la gestion des utilisateurs qui demandent de l'aide */
public class Beneficiary extends User {

    private Mission mission ;

    /***********Constructeur
     * @param firstName
     * @param name
     * @param birthDate
     * @param mail
     * @param phoneNumber
     * @param type**********/
    public Beneficiary(String firstName, String name, String birthDate, String mail, String phoneNumber, TypeUser type) {
        super(firstName, name, birthDate, mail, phoneNumber, type);
    }

    public void createMission() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Give a name to your mission :");
        String missionName = sc.nextLine();
        System.out.println("Give a description to your mission :");
        String description = sc.nextLine();
        System.out.println("What date do you want your mission to be realised");
        String expirationDate = sc.nextLine();
        System.out.println("Where is your mission located ?");
        String location = sc.nextLine();

        Boolean answerOk = false;
        while(!answerOk) {
            System.out.println("Do you have a health professional supervisor ? yes or no");
            String answer = sc.nextLine();
            switch (answer) {
                case "yes":
                    System.out.println("Who is your health professional ?");
                    String healthPro = sc.nextLine();
                    mission = new Mission(missionName,description,expirationDate,location,healthPro);
                    base.addMission(this.getName(),mission);
                    answerOk = true;
                    break;
                case "no":
                    mission = new Mission(missionName,description,expirationDate,location);
                    base.addMission(this.getName(),mission);
                    answerOk = true;
                    break;
                default:
                    System.out.println("Please rewrite your answer (yes or no), we didn't understand");
            }
        }
        sc.close();
    }
}
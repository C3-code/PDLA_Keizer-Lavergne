package controller;

import model.Mission;

import java.sql.SQLException;
import java.util.Scanner;

import static controller.MainProgram.base;
import static controller.UserConnection.thisUser;

public class MissionCreation {


    public static void createMission(String name, String description, String date, String location, String healthPro) throws SQLException {
        Mission mission;
        if (healthPro.equals("yes")){ //il faudra gérer le cas où y a un superviseur justement
            mission = new Mission(thisUser.getName(), name, description, date, location, "healthPro");
        }
        else {
            mission = new Mission(thisUser.getName(), name, description, date, location);
        }
        base.addMission(thisUser.getName(), mission);

    }
}

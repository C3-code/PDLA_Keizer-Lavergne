package controller;

import model.Avis;
import model.Mission;

import java.sql.SQLException;

import static controller.UserConnection.thisUser;

public class CommentCreation {

    public static Avis createCommentFromMission(String comment, Mission mission,int id) throws SQLException {

        Avis avis;
        if (thisUser.getType().equals("BENEFICIARY")) {
            avis = new Avis(mission.getMissionName(), id, mission.getDate(), comment, mission.getVolunteer());
        } else {
            avis = new Avis(mission.getMissionName(), id, mission.getDate(), comment, mission.getBeneficiary());
        }
        return avis;

    }

    /*public static void saveComment(Avis avis) throws SQLException {
        MainProgram.base.addComment(avis);
    }*/

}

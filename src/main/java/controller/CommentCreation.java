package controller;

import model.Avis;
import model.Mission;

import java.sql.SQLException;

import static controller.UserConnection.thisUser;

public class CommentCreation {

    public static void createCommentFromMission(String comment, int id) throws SQLException {

        Mission mission = MainProgram.base.getMissionFromId(id);


        Avis avis;

        if (thisUser.getType().equals("BENEFICIARY")) {
            avis = new Avis(mission.getMissionName(), id, mission.getDate(), comment, mission.getVolunteer());
        } else {
            avis = new Avis(mission.getMissionName(), id, mission.getDate(), comment, mission.getBeneficiary());
        }

        MainProgram.base.addComment(avis);
    }
}

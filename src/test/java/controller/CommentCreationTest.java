package controller;


import static model.User.TypeUser.BENEFICIARY;
import static model.User.TypeUser.VOLUNTEER;
import static org.junit.jupiter.api.Assertions.*;

import model.Avis;
import model.Mission;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;


public class CommentCreationTest {

    private Mission mission;

    @BeforeEach
    void setUp() {
        UserConnection.thisUser = new User("TestUser", "Test", "05/11/2005", "testuser@mail.com", "0123456789", BENEFICIARY);
        mission = new Mission("testuser@mail.com", "Mission Test", "this is a test", "19/12/2024", "INSA");

    }


    @Test
    void testCreateCommentFromMissionBeneficiary() throws SQLException {
        // Préparer les variables de test
        String comment = "Great mission!";
        int id = 1;

        // Appeler la méthode
        Avis result = CommentCreation.createCommentFromMission(comment, mission, id);

        // Vérifier les assertions
        assertNotNull(result);
        assertEquals("Mission Test", result.getMissionName());
        assertEquals(id, result.getMissionId());
        assertEquals(comment, result.getComment());
        assertEquals(mission.getDate(), result.getCommentDate());
        assertEquals(mission.getVolunteer(), result.getDestinataire());  // En tant que bénéficiaire
    }

    @Test
    void testCreateCommentFromMissionVolunteer() throws SQLException {
        UserConnection.thisUser.setType(VOLUNTEER);
        String comment = "Great mission!";
        int id = 1;

        // Appeler la méthode
        Avis result = CommentCreation.createCommentFromMission(comment, mission, id);

        // Vérifier les assertions
        assertNotNull(result);
        assertEquals("Mission Test", result.getMissionName());
        assertEquals(id, result.getMissionId());
        assertEquals(comment, result.getComment());
        assertEquals(mission.getDate(), result.getCommentDate());
        assertEquals(mission.getBeneficiary(), result.getDestinataire());  // En tant que bénéficiaire
    }

}

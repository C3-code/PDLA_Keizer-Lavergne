package controller;

import junit.framework.TestCase;

import static model.User.TypeUser.BENEFICIARY;
import static org.junit.jupiter.api.Assertions.*;

import model.Mission;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class CommentCreationTest {

    private User thisUser;
    private Mission mission;

    @BeforeEach
    void setUp() {
        UserConnection.thisUser = new User("TestUser", "Test", "05/11/2005", "testuser@mail.com", "0123456789", BENEFICIARY);
    }

    /*
    @Test
    void testCreateCommentFromMissionBeneficiary() throws SQLException {
        // Préparer les variables de test
        String comment = "Great mission!";
        int id = 1;

        // Appeler la méthode
        Avis result = yourClass.createCommentFromMission(comment, mission, id);

        // Vérifier les assertions
        assertNotNull(result);
        assertEquals("Mission Test", result.getMissionName());
        assertEquals(id, result.getId());
        assertEquals(comment, result.getComment());
        assertEquals(mission.getDate(), result.getDate());
        assertEquals(mission.getVolunteer(), result.getVolunteer());  // En tant que bénéficiaire
    }

    @Test
    void testCreateCommentFromMissionNonBeneficiary() throws SQLException {
        // Changer le type d'utilisateur
        thisUser.setType("OTHER_TYPE");  // Ce n'est pas un bénéficiaire
        mission = new Mission("Mission Test", new Date(), "Volunteer Name", "Beneficiary Name");

        // Préparer les variables de test
        String comment = "Amazing experience!";
        int id = 2;

        // Appeler la méthode
        Avis result = yourClass.createCommentFromMission(comment, mission, id);

        // Vérifier les assertions
        assertNotNull(result);
        assertEquals("Mission Test", result.getMissionName());
        assertEquals(id, result.getId());
        assertEquals(comment, result.getComment());
        assertEquals(mission.getDate(), result.getDate());
        assertEquals(mission.getBeneficiary(), result.getBeneficiary());  // En tant que non-bénéficiaire
    }*/
}

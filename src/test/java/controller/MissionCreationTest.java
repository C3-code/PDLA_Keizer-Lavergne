package controller;

import controller.MissionCreation;
import model.Mission;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static model.User.TypeUser.BENEFICIARY;
import static model.User.TypeUser.VOLUNTEER;
import static org.junit.jupiter.api.Assertions.*;

class MissionCreationTest {

    @BeforeEach
    void setUp() {
        // Configuration avant chaque test si nécessaire
        // Par exemple, on peut initialiser un utilisateur statique ou d'autres objets nécessaires
        UserConnection.thisUser = new User("TestUser", "Test", "05/11/2005", "testuser@mail.com", "0123456789", BENEFICIARY);
        //MainProgram.base = new MainProgram.Base();  // Supposons que vous avez une classe Base qui gère les missions
    }

    @Test
    void testCreateMissionWithoutHealthPro() throws SQLException {
        // Paramètres pour la mission
        String name = "Mission1";
        String description = "Description de la mission";
        String date = "2024-12-31";
        String location = "Toulouse";
        String healthPro = "no";

        // Appeler la méthode de création de mission
        Mission createdMission = MissionCreation.createMission(name, description, date, location, healthPro);

        // Récupérer la mission dans la "base" pour vérifier si elle a été ajoutée correctement
        //Mission createdMission = MainProgram.base.getMissionByName(name); // Assurez-vous que vous avez cette méthode dans Base

        // Vérifier que l'objet Mission a été créé avec les bons paramètres
        assertNotNull(createdMission);
        assertEquals("testuser@mail.com", createdMission.getBeneficiary());
        assertEquals(name, createdMission.getMissionName());
        assertEquals(description, createdMission.getDescription());
        assertEquals(date, createdMission.getDate());
        assertEquals(location, createdMission.getLocation());
        assertNull(createdMission.getHealthPro());  // Vérifier que le champ healthPro est null (non défini)
    }

    @Test
    void testCreateMissionWithHealthPro() throws SQLException {
        // Paramètres pour la mission
        String name = "Mission2";
        String description = "Mission avec HealthPro";
        String date = "2024-12-31";
        String location = "Paris";
        String healthPro = "yes";

        // Appeler la méthode de création de mission
        Mission createdMission = MissionCreation.createMission(name, description, date, location, healthPro);

        // Récupérer la mission dans la "base" pour vérifier si elle a été ajoutée correctement
        //Mission createdMission = MainProgram.base.getMissionByName(name); // Assurez-vous que vous avez cette méthode dans Base

        // Vérifier que l'objet Mission a été créé avec les bons paramètres, et qu'il a le type "healthPro"
        assertNotNull(createdMission);
        assertEquals("testuser@mail.com", createdMission.getBeneficiary());
        assertEquals(name, createdMission.getMissionName());
        assertEquals(description, createdMission.getDescription());
        assertEquals(date, createdMission.getDate());
        assertEquals(location, createdMission.getLocation());
        assertEquals("healthPro", createdMission.getHealthPro());  // Vérifier que le champ healthPro est correctement défini
    }
}

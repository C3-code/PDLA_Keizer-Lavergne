package controller;

import static org.junit.jupiter.api.Assertions.*;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserConnectionTest {

    // Méthode de test pour vérifier l'initialisation d'un BENEFICIARY
    @Test
    public void testSetUpUser_Beneficiary() throws SQLException {
        User user = UserConnection.setUpUser("User", "One", "01/01/1990", "user.one@example.com", "1234567890", "BENEFICIARY");
        assertNotNull(user);
        assertEquals("User", user.getFirstname());
        assertEquals("One", user.getName());
        assertEquals("01/01/1990", user.getBirthDate());
        assertEquals("user.one@example.com", user.getMail());
        assertEquals("1234567890", user.getPhoneNumber());
        assertEquals(String.valueOf(User.TypeUser.BENEFICIARY), user.getType());
    }

    // Méthode de test pour vérifier l'initialisation d'un VOLUNTEER
    @Test
    public void testSetUpUser_Volunteer() throws SQLException {
        User user = UserConnection.setUpUser("User", "Two", "01/01/2000", "user.two@example.com", "2345678901", "VOLUNTEER");
        assertNotNull(user);
        assertEquals("User", user.getFirstname());
        assertEquals("Two", user.getName());
        assertEquals("01/01/2000", user.getBirthDate());
        assertEquals("user.two@example.com", user.getMail());
        assertEquals("2345678901", user.getPhoneNumber());
        assertEquals(String.valueOf(User.TypeUser.VOLUNTEER), user.getType());
    }

    // Méthode de test pour vérifier l'initialisation d'un HEALTHPRO
    @Test
    public void testSetUpUser_HealthPro() throws SQLException {
        User user = UserConnection.setUpUser("User", "Three", "01/01/1995", "user.three@example.com", "3456789012", "HEALTHPRO");
        assertNotNull(user);
        assertEquals("User", user.getFirstname());
        assertEquals("Three", user.getName());
        assertEquals("01/01/1995", user.getBirthDate());
        assertEquals("user.three@example.com", user.getMail());
        assertEquals("3456789012", user.getPhoneNumber());
        assertEquals(String.valueOf(User.TypeUser.HEALTHPRO), user.getType());
    }

    // Méthode de test pour vérifier l'initialisation d'un ADMIN


    // Méthode de test pour vérifier qu'un rôle non défini lance une exception
    @Test
    public void testSetUpUser_InvalidRole() {
        assertThrows(RuntimeException.class, () -> {
            UserConnection.setUpUser("Invalid", "Role", "19/12/2024", "invalid.role@example.com", "4567890123", "UNKNOWN_ROLE");
        });
    }
}


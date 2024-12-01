package view;
import controller.*;

import javax.swing.*;
import java.sql.SQLException;

public class CreateAccountView extends JFrame{

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField birthDateField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JComboBox<String> roleComboBox;

    public void showSubscriptionField()  {
        setTitle("Create Account");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel pour le formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Champs pour les informations de l'utilisateur
        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Birth Date (XX/YY/YYYY):"));
        birthDateField = new JTextField();
        formPanel.add(birthDateField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneNumberField = new JTextField();
        formPanel.add(phoneNumberField);

        formPanel.add(new JLabel("Role:"));
        String[] roles = {"BENEFICIARY", "VOLUNTEER", "HEALTHPRO", "ADMIN"};
        roleComboBox = new JComboBox<>(roles);
        formPanel.add(roleComboBox);

        // Bouton pour soumettre le formulaire
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                submitForm();
                JOptionPane.showMessageDialog(formPanel, "Subscription saved successfully");
            } catch (SQLException ex) {
                System.out.println("Error submitting the form.");
                JOptionPane.showMessageDialog(formPanel, "Error submitting the form.");
                throw new RuntimeException(ex);
            }
        });
        formPanel.add(submitButton);

        JButton connectPageButton = new JButton("If you already have an account, please connect");
        connectPageButton.addActionListener(e -> {
            UserConnection.showConnectionView();
            formPanel.setVisible(false);
        });

        formPanel.add(connectPageButton);


        getContentPane().add(formPanel);
        setVisible(true);
    }

    // Méthode pour récupérer les données du formulaire
    private void submitForm() throws SQLException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String birthDate = birthDateField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String role = (String) roleComboBox.getSelectedItem();

        // Appeler la méthode du contrôleur pour traiter ces données
        //assert role != null;
        UserConnection.createUser(firstName,lastName,birthDate,email,phoneNumber,role);

        // Remarque : Le contrôleur va récupérer ces données et créer l'utilisateur
        System.out.println("Form submitted with data: " + firstName + " " + lastName+ " "+email);
        //UserConnection.showConnectionView();
    }
}

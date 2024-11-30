package view;

import controller.UserConnection;

import javax.swing.*;
import java.sql.SQLException;

public class ConnectionView extends JFrame {

        private JTextField mailField;


        public void showConnectionField()  {
            setTitle("Connection Page");
            setSize(400, 350);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Panel pour le formulaire
            JPanel connectionPanel = new JPanel();
            connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.Y_AXIS));

            // Champs pour les informations de l'utilisateur
            connectionPanel.add(new JLabel("Enter your email address:"));
            mailField = new JTextField();
            connectionPanel.add(mailField);

            // Bouton pour soumettre le formulaire
            JButton connectButton = new JButton("Connect");
            connectButton.addActionListener(e -> {
                try {
                    submitConnection();
                    setVisible(false);
                } catch (SQLException ex) {
                    System.out.println("Error connecting.");
                    throw new RuntimeException(ex);
                }
            });
            connectionPanel.add(connectButton);

            JButton mainPageButton = new JButton("Main Page");
            connectionPanel.add(mainPageButton);

            getContentPane().add(connectionPanel);
            setVisible(true);
        }

        // Méthode pour récupérer les données du formulaire
        private void submitConnection() throws SQLException {
            String mail = mailField.getText();
            // Appeler la méthode du contrôleur pour traiter ces données
            UserConnection.connectUser(mail);
        }

}

package view;

import controller.GestionBdd;
import controller.UserConnection;

import javax.swing.*;
import java.sql.SQLException;

import static java.lang.Integer.decode;

public class ConnectionView extends JFrame {

        private JTextField mailField;


        public void showConnectionField()  {
            setTitle("Connection Page");
            setSize(600, 400);
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
                String mail = mailField.getText();
                try {
                    if (mail.isEmpty()) { //Si le champs "mail" n'est pas rempli
                        JOptionPane.showMessageDialog(connectionPanel, "Veuillez entrer une adresse mail");
                    } else if (!GestionBdd.getInstance().userExists(mail)) { // si le mail n'est pas valide
                        JOptionPane.showMessageDialog(connectionPanel, "Adresse non valide. Avez-vous créé votre compte au préalable ?");
                        mailField.setText("");//reinitialiser le champ
                    } else { //Si le mail est correct
                        //Traiter la participation à la mission
                        submitConnection();
                        setVisible(false);
                        mailField.setText(""); // Réinitialiser le champ "numéro de mission" après soumission
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            });



            connectionPanel.add(connectButton);

            JButton createPageButton = new JButton("If you don't have an account, create a profile");
            createPageButton.addActionListener(e -> {
                UserConnection.showCreateAccountView();
                connectionPanel.setVisible(false);
            });

            connectionPanel.add(createPageButton);

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

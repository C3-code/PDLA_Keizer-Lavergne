package view;
import javax.swing.*;
import java.awt.*;

import controller.*;



public class WelcomeView extends JFrame {

    // Constructeur pour initialiser l'interface graphique
    public static void createWelcomeView() {
        // Create and set up the window.
        JFrame frame = new JFrame("Help App");
        frame.setSize(400, 250); // Taille de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal avec disposition verticale
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Disposition verticale

        // Créer et configurer le label pour afficher le message de bienvenue
        JLabel greeter = new JLabel("****************   Welcome to the Help App   ****************");
        greeter.setForeground(new Color(125, 125, 125));
        greeter.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement
        panel.add(greeter);

        // Ajouter un espace entre le texte et les boutons
        panel.add(Box.createVerticalStrut(20)); // Espace de 20 pixels

        // Créer le bouton "Create an account"
        JButton createAccountButton = new JButton("Create an account");
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement
        panel.add(createAccountButton);

        // Créer le bouton "Login"
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement
        panel.add(loginButton);

        // Ajouter le panel principal à la fenêtre
        frame.getContentPane().add(panel);

        // ActionListener pour le bouton "Create an account"
        // Action pour le bouton "Create an account"
        createAccountButton.addActionListener(e -> UserConnection.showCreateAccountView() );
        loginButton.addActionListener(e -> UserConnection.showConnectionView() );


        // Afficher la fenêtre
        frame.setVisible(true);
    }

}


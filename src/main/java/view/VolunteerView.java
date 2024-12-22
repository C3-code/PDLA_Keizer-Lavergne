package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import controller.*;
import model.GestionBdd;

import static java.lang.Integer.decode;
import static view.BeneficiaryView.showCommentCreationForm;
import static view.WelcomeView.showWelcomeView;

public class VolunteerView extends JFrame {

    // Constructeur pour initialiser l'interface graphique
    public static void showVolunteerView() {
        /***  Creation et configuration de la fenetre principale ***/
        JFrame frame = new JFrame("Your Profile");
        frame.setSize(1000, 600); // Taille de la fenêtre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal avec disposition verticale
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Disposition verticale

        /***  Creation et configuration des boutons de navigation présents en haut de la page ***/
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Disposition horizontale pour les boutons

        JButton catalogButton = new JButton("Catalog of available missions");
        JButton currentMissionsButton = new JButton("My current missions");
        JButton previousMissionsButton = new JButton("My previous missions");
        JButton commentsButton = new JButton("Comments left on me");

        buttonPanel.add(catalogButton);
        buttonPanel.add(currentMissionsButton);
        buttonPanel.add(previousMissionsButton);
        buttonPanel.add(commentsButton);

        panel.add(buttonPanel);

        /***  Creation et configuration d'une zone de texte permettant l'affichage des missions au milieu de la page ***/
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(450, 370));

        // Ajouter un JScrollPane pour que le texte soit défilable
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);

        /***  Panneau pour participer à une mission (modifié pour être plus compact) ***/
        JPanel participatePanel = new JPanel();
        participatePanel.setLayout(new BoxLayout(participatePanel, BoxLayout.Y_AXIS)); // Disposition verticale
        participatePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le panel

        // Titre du panneau
        JLabel participateLabel = new JLabel("Participer à une mission");
        participateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        participateLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Taille de police plus petite
        participatePanel.add(participateLabel);

        // Champ de texte pour entrer le numéro de mission
        JLabel missionNumberLabel = new JLabel("N° de mission:");
        missionNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        participatePanel.add(missionNumberLabel);

        JTextField missionNumberField = new JTextField(10); // Champ de texte plus petit
        participatePanel.add(missionNumberField);

        // Bouton pour soumettre la demande
        JButton participateButton = new JButton("Soumettre");
        participatePanel.add(participateButton);

        JButton disconnectButton = new JButton("Disconnect");
        participatePanel.add(disconnectButton);

        // Ajouter un peu d'espacement entre les éléments
        participatePanel.add(Box.createVerticalStrut(10));

        // Ajouter ce panneau à la fenêtre
        panel.add(participatePanel);

        // Ajouter le panel principal à la fenêtre
        frame.getContentPane().add(panel);

        /*** Gestion des boutons de la fenetre "profil utilisateur" ***/
        catalogButton.addActionListener(e -> {
            String allMissions = null;
            try {
                allMissions = GestionBdd.getInstance().getOpenMissions();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(allMissions);
        });

        currentMissionsButton.addActionListener(e -> {
            String currentMissions = null;
            try {
                currentMissions = GestionBdd.getInstance().getCurrentMissions();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(currentMissions);
        });

        previousMissionsButton.addActionListener(e -> {
            //User mcqueen = new User("prenom", "nom", "naissance", "mcqueen", "070707", User.TypeUser.VOLUNTEER);
            String previousMissions = null;
            try {
                previousMissions = GestionBdd.getInstance().getPreviousMissions(UserConnection.getInstance());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(previousMissions);
        });

        commentsButton.addActionListener(e -> {
            String comments = null;
            try {
                comments = GestionBdd.getInstance().getComments();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(comments);
        });

        // ActionListener pour le bouton "Soumettre la demande"
        participateButton.addActionListener(e -> {
            String missionNumber = missionNumberField.getText();
            try {
                if (missionNumber.isEmpty()) { //Si le champs "numero de mission" n'est pas rempli
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un numéro de mission.");
                } else if (!GestionBdd.getInstance().availableMission(missionNumber)) { // si le numero entre n'est pas valide
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer le numéro d'une mission disponible.");
                    missionNumberField.setText("");//reinitialiser le champ
                } else { //Si le numero est correct
                    //Traiter la participation à la mission
                    GestionBdd.getInstance().participateInMission(decode(missionNumber),UserConnection.getInstance().getMail());
                    JOptionPane.showMessageDialog(frame, "Demande de participation soumise !");
                    missionNumberField.setText(""); // Réinitialiser le champ "numéro de mission" après soumission
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });
        JPanel leaveCommentPanel = new JPanel();
        leaveCommentPanel.setLayout(new BoxLayout(leaveCommentPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        leaveCommentPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le panel

        JButton LeaveCommentButton = new JButton("Leave a comment");
        leaveCommentPanel.add(LeaveCommentButton);

        LeaveCommentButton.addActionListener(e -> showCommentCreationForm());

        panel.add(leaveCommentPanel);

        disconnectButton.addActionListener(e -> {
            showWelcomeView();
            frame.setVisible(false);
        });

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(VolunteerView::showVolunteerView);
    }*/
}

package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;

import controller.*;
import model.Mission;
import model.User;

import static java.lang.Integer.decode;
import static view.WelcomeView.showWelcomeView;
import controller.MainProgram;

public class BeneficiaryView extends JFrame {


    private static JTextField missionNameField;
    private static JTextField missionDescriptionField;
    private static JTextField missionDateField;
    private static JTextField missionLocationField;

    private static JComboBox<String> healthProField;

    // Constructeur pour initialiser l'interface graphique
    public static void showBeneficiaryView() {
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

        JButton catalogButton = new JButton("My posted missions");
        JButton currentMissionsButton = new JButton("My accepted missions");
        JButton previousMissionsButton = new JButton("My previous missions");

        buttonPanel.add(catalogButton);
        buttonPanel.add(currentMissionsButton);
        buttonPanel.add(previousMissionsButton);

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

        /***  Panneau pour terminer une mission (modifié pour être plus compact) ***/
        JPanel endMissionPanel = new JPanel();
        endMissionPanel.setLayout(new BoxLayout(endMissionPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        endMissionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le panel

        // Titre du panneau
        JLabel endMissionLabel = new JLabel("Marquer une mission comme terminée");
        endMissionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endMissionLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Taille de police plus petite
        endMissionPanel.add(endMissionLabel);

        // Champ de texte pour entrer le numéro de mission
        JLabel missionNumberLabel = new JLabel("N° de mission:");
        missionNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        endMissionPanel.add(missionNumberLabel);

        JTextField missionNumberField = new JTextField(10); // Champ de texte plus petit
        endMissionPanel.add(missionNumberField);

        // Bouton pour soumettre la demande
        JButton endMissionButton = new JButton("Soumettre");
        endMissionPanel.add(endMissionButton);

        JButton disconnectButton = new JButton("Disconnect");
        endMissionPanel.add(disconnectButton);

        // Ajouter un peu d'espacement entre les éléments
        endMissionPanel.add(Box.createVerticalStrut(10));

        // Ajouter ce panneau à la fenêtre
        panel.add(endMissionPanel);

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



        // ActionListener pour le bouton "Soumettre la demande"
        endMissionButton.addActionListener(e -> {
            String missionNumber = missionNumberField.getText();
            try {
                if (missionNumber.isEmpty()) { //Si le champs "numero de mission" n'est pas rempli
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un numéro de mission.");
                } else if (!GestionBdd.getInstance().availableMission(missionNumber)) { // si le numero entre n'est pas valide
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer le numéro d'une mission disponible.");
                    missionNumberField.setText("");//reinitialiser le champ
                } else { //Si le numero est correct
                    //Traiter la participation à la mission
                    GestionBdd.getInstance().endMission(decode(missionNumber));
                    JOptionPane.showMessageDialog(frame, "Mission terminée !");
                    missionNumberField.setText(""); // Réinitialiser le champ "numéro de mission" après soumission
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        disconnectButton.addActionListener(e -> {
            showWelcomeView();
            frame.setVisible(false);
        });


        JPanel createMissionPanel = new JPanel();
        createMissionPanel.setLayout(new BoxLayout(createMissionPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        createMissionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le panel

        JButton createMissionButton = new JButton("Create a new mission");
        createMissionPanel.add(createMissionButton);

        createMissionButton.addActionListener(e -> showMissionCreationForm());

        panel.add(createMissionPanel);

        JPanel leaveCommentPanel = new JPanel();
        leaveCommentPanel.setLayout(new BoxLayout(leaveCommentPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        leaveCommentPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le panel

        JButton LeaveCommentButton = new JButton("Leave a comment");
        leaveCommentPanel.add(LeaveCommentButton);

        LeaveCommentButton.addActionListener(e -> showCommentCreationForm());

        panel.add(leaveCommentPanel);

        // Afficher la fenêtre
        frame.setVisible(true);
    }


    private static void showMissionCreationForm() {
        // Créer une fenêtre de type JDialog
        JDialog missionFormDialog = new JDialog();
        missionFormDialog.setTitle("Create a New Mission");
        missionFormDialog.setSize(400, 300); // Taille de la fenêtre
        missionFormDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Panneau principal pour le formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Champ de texte pour le nom de la mission
        formPanel.add(new JLabel("Mission Name:"));
        missionNameField = new JTextField();
        formPanel.add(missionNameField);

        formPanel.add(new JLabel("Description:"));
        missionDescriptionField = new JTextField();
        formPanel.add(missionDescriptionField);

        formPanel.add(new JLabel("Date of execution (DD/MM/YYY):"));
        missionDateField = new JTextField();
        formPanel.add(missionDateField);

        formPanel.add(new JLabel("Location:"));
        missionLocationField = new JTextField();
        formPanel.add(missionLocationField);

        formPanel.add(new JLabel("Do you have a health professionnal ?"));
        String[] roles = {"yes", "no"};
        healthProField = new JComboBox<>(roles);
        formPanel.add(healthProField);

        // Ajouter un bouton pour soumettre la mission
        JButton submitButton = new JButton("Submit Mission");
        submitButton.addActionListener(e -> {
            String missionName = missionNameField.getText();
            String missionDescription = missionDescriptionField.getText();
            String missionDate = missionDateField.getText();
            String missionLocation = missionLocationField.getText();
            String healthPro = (String) healthProField.getSelectedItem();

            if (missionName.isEmpty() || missionDescription.isEmpty() || missionDate.isEmpty() || missionLocation.isEmpty() || Objects.requireNonNull(healthPro).isEmpty()) {
                JOptionPane.showMessageDialog(missionFormDialog, "Please fill in all fields.");
            } else {
                try {
                    // Appeler une méthode de la classe GestionBdd pour enregistrer la mission
                    // Exemple : GestionBdd.getInstance().createMission(missionName, missionDescription, missionDate, missionType);
                    MissionCreation.createMission(missionName, missionDescription, missionDate, missionLocation, healthPro);
                    JOptionPane.showMessageDialog(missionFormDialog, "Mission created successfully!");
                    missionFormDialog.dispose(); // Fermer la fenêtre une fois la mission soumise
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(missionFormDialog, "Error creating mission: " + ex.getMessage());
                }
            }
        });
        formPanel.add(submitButton);

        // Ajouter le formulaire au dialog et afficher la fenêtre
        missionFormDialog.getContentPane().add(formPanel);
        missionFormDialog.setVisible(true);
    }

    private static void showCommentCreationForm() {
        // Créer une fenêtre de type JDialog
        JDialog missionFormDialog = new JDialog();
        missionFormDialog.setTitle("Leave a comment");
        missionFormDialog.setSize(400, 300); // Taille de la fenêtre
        missionFormDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Panneau principal pour le formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Champ de texte pour le nom de la mission
        formPanel.add(new JLabel("Please leave a comment"));
        JTextField commentField = new JTextField();
        formPanel.add(commentField);

        formPanel.add(new JLabel("What's the id of the mission you want to leave a comment for ?"));
        JTextField missionIdField = new JTextField();
        formPanel.add(missionIdField);


        // Ajouter un bouton pour soumettre la mission
        JButton submitButton = new JButton("Submit Comment");
        submitButton.addActionListener(e -> {
            String commentDescription = commentField.getText();
            int missionId = Integer.parseInt(missionIdField.getText());


            try {
                if (commentDescription.isEmpty() || !MainProgram.base.missionExists(missionId)) {
                    JOptionPane.showMessageDialog(missionFormDialog, "Please fill in all fields and chose a valid mission id.");
                } else {
                    try {
                        MainProgram.base.createCommentFromMissionId(missionId);
                        JOptionPane.showMessageDialog(missionFormDialog, "Comment left successfully!");
                        missionFormDialog.dispose(); // Fermer la fenêtre une fois la mission soumise
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(missionFormDialog, "Error adding a comment: " + ex.getMessage());
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        formPanel.add(submitButton);

        // Ajouter le formulaire au dialog et afficher la fenêtre
        missionFormDialog.getContentPane().add(formPanel);
        missionFormDialog.setVisible(true);
    }

}

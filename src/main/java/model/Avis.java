package model;

public class Avis {

    private String missionName;
    private int missionId;
    private String commentDate;
    private String commentaire;
    private String destinataire;

    public Avis(String mission, int id, String date, String commentaire, String destinataire) {
        this.missionName = mission;
        this.missionId = id;
        this.commentDate = date;
        this.commentaire = commentaire;
        this.destinataire = destinataire;
    }

    public String getMissionName() {
        return missionName;
    }

    public int getMissionId() {
        return missionId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getDestinataire() {
        return destinataire;
    }
}

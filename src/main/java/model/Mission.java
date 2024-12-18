package model;

public class Mission {
    private String missionName;
    private String description;
    private String date;
    private String location;

    private String healthPro;

    private String beneficiary;

    private String volunteer;

    /** Pour une mission plusieurs états sont possibles
     *
     */
    private String state;



    public Mission(String beneficiary, String missionName, String description, String date, String location, String healthPro) {
        this.beneficiary = beneficiary;
        this.missionName = missionName;
        this.description = description;
        this.date = date;
        this.location = location;
        this.healthPro = healthPro;
        this.volunteer = "none";
    }

    public Mission(String beneficiary, String missionName, String description, String date, String location) {
        this.beneficiary = beneficiary;
        this.missionName = missionName;
        this.description = description;
        this.date = date;
        this.location = location;
        this.volunteer = "none";
    }



    /************GETTERS***************/

    public void updateVolunteer(String volunteer) {
        this.volunteer = volunteer;
    }

    public String getBeneficiary() {return beneficiary;}
    public String getMissionName() {
        return missionName;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getHealthPro() {
        return healthPro;
    }

    public String getVolunteer() {
        return volunteer;
    }
}

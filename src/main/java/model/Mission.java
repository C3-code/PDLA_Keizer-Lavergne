package model;

public class Mission {
    private String missionName;
    private String description;
    private String date;
    private String location;

    private String healthPro;

    /** Pour une mission plusieurs états sont possibles
     *
     */
    private String state;



    public Mission(String missionName, String description, String date, String location, String healthPro) {
        this.missionName = missionName;
        this.description = description;
        this.date = date;
        this.location = location;
        this.healthPro = healthPro;
    }

    public Mission(String missionName, String description, String date, String location) {
        this.missionName = missionName;
        this.description = description;
        this.date = date;
        this.location = location;
    }



    /************GETTERS***************/
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
}

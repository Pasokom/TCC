package db.pojo;

/**
 * AppointmentDB
 */
public class AppointmentDB {

    private String type;

    public AppointmentDB(String type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
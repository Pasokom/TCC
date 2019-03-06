package db.pojo;

/**
 * Moon
 */
public class Moon extends AppointmentDB {

    private int phase;
    private String description;

    public Moon() {
        super("moon");
    }

    /**
     * @return the type
     */
    public int getPhase() {
        return phase;
    }

    /**
     * @param type the type to set
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
package db.pojo;

import java.util.ArrayList;

public class DayDB {

    private int day;
    private ArrayList<AppointmentDB> appointments;

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the appointments
     */
    public ArrayList<AppointmentDB> getAppointments() {
        return appointments;
    }

    /**
     * @param appointments the appointments to set
     */
    public void setAppointments(ArrayList<AppointmentDB> appointments) {
        this.appointments = appointments;
    }
}
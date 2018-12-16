package db.pojo.reminderPOJO;

import java.util.ArrayList;

public class ReminderDB {

	private int reminderId;
	private String title;
	private boolean active;
	private int recurrenceType;
	private int repetitionType;
	private int recurrence;
	private int userID;

	private ArrayList<ReminderSchedule> lReminderSchedule;

	public ReminderDB() {
		this.lReminderSchedule = new ArrayList<ReminderSchedule>();
	}

	public int getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(int recurrence) {
		this.recurrence = recurrence;
	}

	/**
	 * @return the lReminderSchedule
	 */
	public ArrayList<ReminderSchedule> getlReminderSchedule() {
		return lReminderSchedule;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param recurrenceType the recurrenceType to set
	 */
	public void setRecurrenceType(int recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	/**
	 * @param reminderId the reminderId to set
	 */
	public void setReminderId(int reminderId) {
		this.reminderId = reminderId;
	}

	/**
	 * @param repetitionType the repetitionType to set
	 */
	public void setRepetitionType(int repetitionType) {
		this.repetitionType = repetitionType;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return the recurrenceType
	 */
	public int getRecurrenceType() {
		return recurrenceType;
	}

	/**
	 * @return the reminderId
	 */
	public int getReminderId() {
		return reminderId;
	}

	/**
	 * @return the repetitionType
	 */
	public int getRepetitionType() {
		return repetitionType;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

}

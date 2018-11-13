package db.pojo;

import javafx.scene.text.Text;

public class ReminderDB {

	private Text reminder;
	private String status;
	private String type_recurrence;
	private boolean recurrence_by_minute;
	private boolean all_day;
	private boolean repeat;
	private int user_cod;

	public ReminderDB() {
		this.reminder = new Text();
	}
	public String getType_recurrence() {
		return type_recurrence;
	}
	public void setType_recurrence(String type_recurrence) {
		this.type_recurrence = type_recurrence;
	}
	public boolean isRecurrence_by_minute() {
		return recurrence_by_minute;
	}
	public void setRecurrence_by_minute(boolean recurrence_by_minute) {
		this.recurrence_by_minute = recurrence_by_minute;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setReminder(Text reminder) {
		this.reminder = reminder;
	}

	public Text getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder.setText(reminder);
	}

	public boolean isAll_day() {
		return all_day;
	}

	public void setAll_day(boolean all_day) {
		this.all_day = all_day;
	}
	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	public void set_user_cod(int cod) {
		this.user_cod = cod;
	}

}

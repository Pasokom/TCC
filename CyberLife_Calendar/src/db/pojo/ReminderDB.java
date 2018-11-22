package db.pojo;

import javafx.scene.text.Text;

public class ReminderDB {

	private Text reminder;
	private boolean status;
	private int type_recurrence;
	private boolean all_day;
	private boolean repeat;
	private int user_cod;

	public int getUser_cod() {
		return user_cod;
	}
	public void setUser_cod(int user_cod) {
		this.user_cod = user_cod;
	}
	public ReminderDB() {
		this.reminder = new Text();
	}
	public int getType_recurrence() {
		return type_recurrence;
	}
	public void setType_recurrence(int type_recurrence) {
		this.type_recurrence = type_recurrence;
	}
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
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

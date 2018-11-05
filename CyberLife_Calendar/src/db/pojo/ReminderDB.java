package db.pojo;

import javafx.scene.text.Text;

public class ReminderDB {

	private Text reminder;

	private boolean all_day;

	private boolean repeat;

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

}

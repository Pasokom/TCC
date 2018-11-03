package db.pojo;

import java.sql.Date;
import java.sql.Time;

/**
 * the tables DATA_HORARIO_LEMBRETE and LEMBRETE
 * 
 * @author jefter66
 *
 */
public class UserReminders {
	/**
	 * reminder table properties
	 */
	private int cod_reminder;
	private String name;
	private String description;
	private String status;
	private boolean all_day;
	/** 
	 * table date and hour
	 */
	private int cod_reminder_repetition;
	private Date date;
	private Time time;

	public int getCod_reminder_repetition() {
		return cod_reminder_repetition;
	}

	public void setCod_reminder_repetition(int cod_reminder_repetition) {
		this.cod_reminder_repetition = cod_reminder_repetition;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getCod_reminder() {
		return cod_reminder;
	}

	public void setCod_reminder(int cod_reminder) {
		this.cod_reminder = cod_reminder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAll_day() {
		return all_day;
	}

	public void setAll_day(boolean all_day) {
		this.all_day = all_day;
	}
}

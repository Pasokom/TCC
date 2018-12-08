package db.pojo.reminderPOJO;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderSchedule {

	private int cod;
	private int fk_reminder;
	private Timestamp datetime_begin;
	private Timestamp datetime_end;
	private Time timeBegin;
	private Time timeEnd;
	private int weekDay;
	private int recurrence;
	private int minutesInterval;
	private int amount_of_repetition;
	private boolean isActive;

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param fk_reminder the fk_reminder to set
	 */
	public void setFk_reminder(int fk_reminder) {
		this.fk_reminder = fk_reminder;
	}

	/**
	 * @return the fk_reminder
	 */
	public int getFk_reminder() {
		return fk_reminder;
	}

	/**
	 * @return the minutesInterval
	 */
	public int getMinutesInterval() {
		return minutesInterval;
	}

	/**
	 * @param minutesInterval the minutesInterval to set
	 */
	public void setMinutesInterval(int minutesInterval) {
		this.minutesInterval = minutesInterval;
	}

	/**
	 * @param timeBegin the timeBegin to set
	 */
	public void setTimeBegin(String timeBegin) {
		if (timeBegin != null)
			try {
				System.out.println(timeEnd);
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				Date date = sdf.parse(timeBegin);
				this.timeBegin = new Time(date.getTime());
				System.out.println(timeBegin);
			} catch (ParseException e) {
				// e.printStackTrace();
				System.out.println("Erro");
			}
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		if (timeEnd != null)
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
				Date date = sdf.parse(timeEnd);
				this.timeEnd = new Time(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
	}

	/**
	 * @return the timeBegin
	 */
	public Time getTimeBegin() {
		return timeBegin;
	}

	/**
	 * @return the timeEnd
	 */
	public Time getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @return the weekDay
	 */
	public int getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	public int getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(int recurrence) {
		this.recurrence = recurrence;
	}

	public int getAmount_of_repetition() {
		return amount_of_repetition;
	}

	public void setAmount_of_repetition(int amount_of_repetition) {
		this.amount_of_repetition = amount_of_repetition;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public void setDatetime_begin(Timestamp datetime_begin) {
		System.out.println(datetime_begin);
		this.datetime_begin = datetime_begin;
	}

	public void setDatetime_end(Timestamp datetime_end) {
		if (datetime_end != null)
			this.datetime_end = datetime_end;
	}

	public Timestamp getDatetime_end() {
		return datetime_end;
	}

	public Timestamp getDatetime_begin() {
		return datetime_begin;
	}
}
package db.pojo.reminderPOJO;

import java.sql.Time;
import java.util.Date;

public class ReminderSchedule {

	private int cod;
	private int fk_reminder;
	private Date datetime_begin;
	private Date datetime_end;
	private Time timeBegin;
	private Time timeEnd;
	private int weekDay;
	private int recurrence;
	private int minutesInterval;
	private int amount_of_repetition;

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
	public void setTimeBegin(Time timeBegin) {
		this.timeBegin = timeBegin;
	}
	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
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

	public Date getDatetime_begin() {
		return datetime_begin;
	}

	public void setDatetime_begin(Date datetime_begin) {
		this.datetime_begin = datetime_begin;
	}

	public Date getDatetime_end() {
		return datetime_end;
	}

	public void setDatetime_end(Date datetime_end) {
		this.datetime_end = datetime_end;
	}
}
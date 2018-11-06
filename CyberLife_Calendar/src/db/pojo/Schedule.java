package db.pojo;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;;

public class Schedule {

	private int cod;
	private Time hour;
	private Date date;

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) throws ParseException {
		this.date = date; // parseDate(date, "dd/MM/yyyy");
	}

	public Time getHour() {
		return hour;
	}

	public void setHour(Time hour) throws ParseException {
		this.hour = new Time(hour.getTime());
	}
}
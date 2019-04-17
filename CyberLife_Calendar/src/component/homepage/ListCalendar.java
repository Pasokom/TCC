package component.homepage;

import java.util.ArrayList;
import java.util.Calendar;

import component.CustomScroll;
import component.appointment.AppointmentComponent;
import db.functions.appointment.LoadAppointment;
import db.functions.event.RetrieveEvents;
import db.pojo.AppointmentDB;
import db.pojo.HolidayDB;
import db.pojo.Moon;
import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import statics.Enums;

public class ListCalendar extends VBox {

	private Label lblSelectedDate, lblWeekDay;
	private VBox vContent, vAllDay;
	private CustomScroll listAllDay;
	private ArrayList<VBox> hours;
	private Calendar currentDate;

	RetrieveEvents retrieveEvents = new RetrieveEvents();

	public ListCalendar(Calendar date) {
		this.currentDate = Calendar.getInstance();
		this.currentDate.setTime(date.getTime());

		this.prefWidthProperty().set(250);
		this.setId("this");

		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());

		addComponents(date);
	}

	private void addComponents(Calendar date) {
		/* Cabe�ario da programa��o */
		HBox hHeader = new HBox();
		hHeader.setId("header");

		this.lblSelectedDate = new Label(date.get(Calendar.DAY_OF_MONTH) + " " + Enums.Month.values()[date.get(Calendar.MONTH)].getValue().substring(0,3));
		lblSelectedDate.setFont(new Font(30));

		hHeader.setAlignment(Pos.CENTER_LEFT);

		hHeader.getChildren().addAll(lblSelectedDate);
		/* Fim do cabe�ario da programa��o */

		/* Dia da semana */
		HBox hDayWeek = new HBox();
		hDayWeek.setId("day_week");

		this.lblWeekDay = new Label(Enums.DayOfWeek.values()[date.get(Calendar.DAY_OF_WEEK) - 1].getValue());
		lblWeekDay.setFont(new Font(20));
		lblWeekDay.setMinHeight(32);
		lblWeekDay.setMaxHeight(32);

		hDayWeek.setAlignment(Pos.CENTER_LEFT);

		hDayWeek.getChildren().addAll(lblWeekDay);
		/* fim dia da semana */

		/* Lista dia todo */
		this.vAllDay = new VBox();
		this.vAllDay.setId("content");
		this.vAllDay.setPadding(new Insets(10, 0, 10, 10));
		this.vAllDay.setSpacing(10);

		this.listAllDay = new CustomScroll();
		vAllDay.prefWidthProperty().bind(listAllDay.widthProperty());
		listAllDay.setComponent(vAllDay);
		listAllDay.setMinViewportHeight(100);
		listAllDay.setId("list");

		this.vContent = new VBox();
		this.vContent.setId("content");
		vContent.setPadding(new Insets(10, 0, 10, 10));
		vContent.setSpacing(10);

		this.getChildren().clear();

		this.getChildren().addAll(hHeader, hDayWeek);
		this.getChildren().add(listAllDay);


		addAppointments(date);
	}

	private void addAppointments(Calendar date){

		ArrayList<AppointmentDB> appointments = new LoadAppointment().loadFromDay(date);

		for (AppointmentDB appointment : appointments) {
			
			AppointmentComponent component;

			switch (appointment.getType()) {
				case "reminder":
					component = new AppointmentComponent((ReminderDB)appointment);
					break;
				case "event":
					component = new AppointmentComponent((EventDB)appointment);
					break;
				case "holiday":
					component = new AppointmentComponent((HolidayDB)appointment);
					break;
				case "moon":
					component = new AppointmentComponent((Moon)appointment);
					break;
				default:
					component = new AppointmentComponent();
					break;
			}

			vAllDay.getChildren().add(component);
		}
	}

	public void update(Calendar date) {
		this.currentDate = date;
		addComponents(this.currentDate);
	}

	public Calendar getCurrentDate(){
		return this.currentDate;
	}
}
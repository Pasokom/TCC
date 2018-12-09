package component.homepage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import component.CustomScroll;
import component.event.EventComponent;
import component.reminder.ReminderComponent;
import db.functions.event.RetrieveEvents;
import db.functions.reminderFUNCTIONS.LoadReminder;
import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;
import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import statics.SESSION;

public class ListCalendar extends VBox {

	private Label lblSelectedDate, lblReminder;
	private VBox vContent, vAllDay;
	private CustomScroll listReminder, listAllDay;
	private ArrayList<VBox> hours;

	RetrieveEvents retrieveEvents = new RetrieveEvents();
	LoadReminder loadReminders;

	public ListCalendar(Calendar date) {

		this.prefWidthProperty().set(250);
		this.setId("this");

		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());

		addComponents(date);
	}

	private void addComponents(Calendar date) {
		/* Cabe�ario da programa��o */
		HBox hHeader = new HBox();
		hHeader.setId("header");

		this.lblSelectedDate = new Label(date.get(Calendar.DAY_OF_MONTH) + "/" + (date.get(Calendar.MONTH) + 1));
		lblSelectedDate.setFont(new Font(30));
		this.lblReminder = new Label(" - Programação");

		hHeader.setAlignment(Pos.CENTER_LEFT);

		hHeader.getChildren().addAll(lblSelectedDate, lblReminder);
		/* Fim do cabe�ario da programa��o */

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

		/* Lista de horarios */
		hours = new ArrayList<>();
		addHours();

		this.vContent = new VBox();
		this.vContent.setId("content");
		vContent.setPadding(new Insets(10, 0, 10, 10));
		vContent.setSpacing(10);
		vContent.getChildren().addAll(hours);

		this.listReminder = new CustomScroll();
		vContent.prefWidthProperty().bind(listReminder.widthProperty());
		listReminder.setComponent(vContent);
		listReminder.setId("list");

		this.getChildren().clear();

		this.getChildren().add(hHeader);
		this.getChildren().add(vAllDay);
		this.getChildren().add(listReminder);

		addEvents(date);
		addReminders(date);
	}

	private void addHours() {

		for (int i = 0; i < 24; i++) {

			VBox hour = new VBox();
			hour.setSpacing(5);

			Label lblHour = new Label(i + ":00");

			VBox hourContent = new VBox();
			hourContent.setSpacing(5);

			Separator separator = new Separator();

			hour.getChildren().addAll(lblHour, hourContent, separator);

			this.hours.add(hour);
		}
	}

	private void addReminders(Calendar date) {
		ArrayList<ReminderDB> reminders = new ArrayList<>();

		try {
			loadReminders = new LoadReminder();
			reminders = loadReminders.getReminders((int) SESSION.get_user_cod(),	LoadReminder.TypeOfQuery.ALL_REMINDERS);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (reminders == null)
			return;

		for (ReminderDB reminder : reminders) {

			Date eventDate = new Date(reminder.getlReminderSchedule().get(0).getDatetime_begin().getTime());

			LocalDate myDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate myEventDate = eventDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (myDate.compareTo(myEventDate) == 0) {

				ReminderComponent rC = new ReminderComponent(reminder);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(reminder.getlReminderSchedule().get(0).getDatetime_begin());

				((VBox) ((VBox) this.vContent.getChildren().get(calendar.get(Calendar.HOUR_OF_DAY))).getChildren()
						.get(1)).getChildren().add(rC);
			}
		}
	}

	private void addEvents(Calendar date) {

		if (HomePage.calendarComponent != null)
			retrieveEvents.updateList(HomePage.calendarComponent.getDate());
		else
			retrieveEvents.updateList(date);
			
		for(EventDB event : RetrieveEvents.listEvents) {
			
			Date eventDate = new Date(event.getData_inicio().getTime());
			
			LocalDate myDate = date.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate myEventDate = eventDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (myDate.compareTo(myEventDate) == 0) {

				EventComponent eC = new EventComponent(event);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(event.getData_inicio());

				if (event.isDia_todo())
					vAllDay.getChildren().add(eC);
				else
					((VBox) ((VBox) this.vContent.getChildren().get(calendar.get(Calendar.HOUR_OF_DAY))).getChildren()
							.get(1)).getChildren().add(eC);
			}
		}
	}

	public void update(Calendar date) {
		addComponents(date);
	}
}
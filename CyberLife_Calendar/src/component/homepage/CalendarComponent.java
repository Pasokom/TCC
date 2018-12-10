package component.homepage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import component.event.EventComponentDemo;
import component.reminder.ReminderComponentDemo;
import db.functions.event.RetrieveEvents;
import db.functions.reminderFUNCTIONS.LoadReminder;
import db.pojo.eventPOJO.EventDB;
import db.pojo.reminderPOJO.ReminderDB;
import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import statics.Enums;
import statics.SESSION;

public class CalendarComponent extends GridPane {

	Calendar current_date;
	Calendar date;
	RetrieveEvents retrieveEvents = new RetrieveEvents();

	public CalendarComponent(Calendar date) {
		this.date = date;

		this.getStylesheets().add(this.getClass().getResource("/css/calendar_component.css").toExternalForm());
		this.setPadding(new Insets(10));

		createCalendar(date);
	}

	public void createCalendar(Calendar date) {
		this.date = date;
		this.getChildren().clear();

		VBox[] demoList = getDemoList();

		Calendar today = Calendar.getInstance();

		/* Pega o primeiro dia do mes */
		date.add(Calendar.DATE, -date.get(Calendar.DATE) + 1);

		Calendar last_day_month = Calendar.getInstance();
		last_day_month.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 
			date.getActualMaximum(Calendar.DAY_OF_MONTH));

		int last_day_week = last_day_month.get(Calendar.DAY_OF_WEEK);
		/* Dia da semana do primeiro dia do mes */
		int first_day_week = date.get(Calendar.DAY_OF_WEEK);
		/* Contador de colunas do calendario */
		int aux1 = 0;
		/* Contador de linha do calendario */
		int aux2 = 0;
		/* Ultimo dia do mes */
		int aux3 = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		/* Pegando mes anterior */
		Calendar beforeMonth = Calendar.getInstance();
		beforeMonth.setTime(date.getTime());
		beforeMonth.add(Calendar.MONTH, -1);
		/* Guarda o ultimo dia do mes anterior */
		int aux4 = beforeMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= aux3 + first_day_week - 1 + (7 - last_day_week); i++) {
			
			Label lblDay;
			
			if(i < first_day_week){
				lblDay = new Label(String.valueOf(aux4 - first_day_week + i + 1));
				lblDay.setId("other_month");
			}
			else if(i - first_day_week + 1 > aux3){
				lblDay = new Label(String.valueOf(i - first_day_week - aux3 + 1));
				lblDay.setId("other_month");
			}
			else{
				lblDay = new Label(String.valueOf(i - first_day_week + 1));
			
				if (lblDay.getText().equals(String.valueOf(today.get(Calendar.DATE)))
				&& date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
				&& date.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {

					lblDay.setId("today");
				}
			}

			final int day = Integer.parseInt(lblDay.getText());

			VBox dateItem = new VBox();
			dateItem.setId("date_item");

			VBox box = new VBox();
			
			if(i <= 7){
				Label dayWeek = new Label(Enums.DayOfWeek.values()[i - 1].getValue());
				dayWeek.setPrefWidth(100);

				if(i == today.get(Calendar.DAY_OF_WEEK) && date.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					dayWeek.setId("today_week");
				else
					dayWeek.setId("day_week");

				box.getChildren().add(dayWeek);
			}
			box.getChildren().add(lblDay);
			dateItem.getChildren().add(box);

			GridPane.setHgrow(dateItem, Priority.ALWAYS);
			GridPane.setVgrow(dateItem, Priority.ALWAYS);

			if(i >= first_day_week && i - first_day_week + 1 < aux3)
				if(demoList[day - 1] != null)
					dateItem.getChildren().add(demoList[day - 1]);

			dateItem.setOnMouseClicked(e -> {

				current_date = Calendar.getInstance();
				current_date.setTime(date.getTime());
				current_date.set(current_date.get(Calendar.YEAR), current_date.get(Calendar.MONTH), day);
				HomePage.listCalendar.update(current_date);
			});

			this.add(dateItem, aux1, aux2);

			aux1++;

			if (aux1 > 6) {

				aux1 = 0;
				aux2++;
			}
		}
	}

	public Calendar getDate(){
		return this.date;
	}

	private VBox[] getDemoList(){

		VBox[] boxes = new VBox[31];

		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = new VBox();
			boxes[i].setSpacing(2);
			boxes[i].setMaxHeight(100);
		}

		retrieveEvents.updateList(this.date);


		for(EventDB event : RetrieveEvents.listEvents) {
		
			if(event.getData_inicio() != null){
				Date eventDate = new Date(event.getData_inicio().getTime());
				Calendar eDate = Calendar.getInstance();
				eDate.setTime(eventDate);

				EventComponentDemo eDemo = new EventComponentDemo(event);

				int month_date = this.date.get(Calendar.MONTH);

				if(eDate.get(Calendar.MONTH) == month_date)
				{
					int eDay = eDate.get(Calendar.DATE);

					if(boxes[eDay - 1].getChildren().size() < 4){

						boxes[eDay - 1].getChildren().add(eDemo);
					}
				}
			}
		}

		ArrayList<ReminderDB> reminders = new ArrayList<>();

		try {
			LoadReminder loadReminders = new LoadReminder();
			reminders = loadReminders.getReminders((int) SESSION.get_user_cod(),
					LoadReminder.TypeOfQuery.ALL_REMINDERS);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		for (ReminderDB reminder : reminders) {
			
			Date eventDate = new Date(reminder.getlReminderSchedule().get(0).getDatetime_begin().getTime());
			Calendar eDate = Calendar.getInstance();
			eDate.setTime(eventDate);

			ReminderComponentDemo rDemo = new ReminderComponentDemo(reminder);

			int month_date = this.date.get(Calendar.MONTH);

			if(eDate.get(Calendar.MONTH) == month_date)
			{
				int eDay = eDate.get(Calendar.DATE);
				if(boxes[eDay - 1].getChildren().size() < 4){

					boxes[eDay - 1].getChildren().add(rDemo);
				}
			}
			
		}

		return boxes;
	}
}
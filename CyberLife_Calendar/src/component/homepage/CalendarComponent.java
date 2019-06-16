package component.homepage;

import java.util.ArrayList;
import java.util.Calendar;

import db.functions.appointment.LoadAppointment;
import db.functions.event.RetrieveEvents;
import db.pojo.AppointmentDB;
import db.pojo.DayDB;
import db.pojo.eventPOJO.EventDB;
import display.scenes.HomePage;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import statics.Enums;
import statics.Enums.DayType;

public class CalendarComponent extends GridPane {

	Calendar current_date;
	Calendar date;
	RetrieveEvents retrieveEvents = new RetrieveEvents();

	public CalendarComponent(Calendar date) {
		this.date = date;

		this.getStylesheets().add(this.getClass().getResource("/css/calendar_component.css").toExternalForm());
		this.setId("this");

		this.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		createCalendar(date);
	}

	public void createCalendar(Calendar date){
		this.date = date;
		this.getChildren().clear();

		this.setHgap(2);
		this.setVgap(2);

		ArrayList<DayDB> days = new LoadAppointment().loadFromMonth(date);

		/* Pega o primeiro dia do mes */
		date.add(Calendar.DATE, -date.get(Calendar.DATE) + 1);

		/* Dia da semana do primeiro dia do mes */
		int first_day_week = date.get(Calendar.DAY_OF_WEEK);
		/* Contador de colunas do calendario */
		int line = first_day_week - 1;
		/* Contador de linha do calendario */
		int column = 1;
		/* Ultimo dia do mes */
		int last_day_month = date.getActualMaximum(Calendar.DAY_OF_MONTH);

		/* Pegando mes anterior */
		Calendar beforeMonth = Calendar.getInstance();
		beforeMonth.setTime(date.getTime());
		beforeMonth.add(Calendar.MONTH, -1);
		/* Guarda o ultimo dia do mes anterior */
		int b_last_day = beforeMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

		/* Adicionando dias da semana no calendario */
		for (int i = 0; i < 7; i++) {
			
			Label lblWeek = new Label(Enums.DayOfWeek.values()[i].getAbrev());
			lblWeek.prefWidthProperty().bind(this.widthProperty());
			lblWeek.setMinHeight(40);
			lblWeek.setMaxHeight(40);
			lblWeek.setId("day_week");
			this.add(lblWeek, i, 0);

			lblWeek.widthProperty().addListener((obs, oldValue, newValue) -> {

				double fontSize = lblWeek.getWidth() * 30 / 100;
				fontSize = fontSize > 20 ? 20 : fontSize;

				lblWeek.setFont(new Font(fontSize));
			});
		}

		/* Adicionando dias do mes anterior no calendario */
		for (int i = 0; i < first_day_week; i++) {
			
			EventDB event = new EventDB();
			event.setTitulo("mes anterior");

			ArrayList<AppointmentDB> list = new ArrayList<>();
			
			int day = b_last_day - (first_day_week - 1) + (i + 1);

			CalendarDayComponent dateItem = new CalendarDayComponent(day, list, DayType.OTHER_MONTH);
	
			dateItem.setPrefSize(200, 200);

			GridPane.setHgrow(dateItem, Priority.NEVER);
			GridPane.setVgrow(dateItem, Priority.NEVER);
	
			this.add(dateItem, i, column);
		}

		/* Adicionando dias do mes atual no calendario */
		for(int i = 0; i < last_day_month; i++){

			int calendar_day = i + 1;

			EventDB event = new EventDB();
			event.setTitulo("Exemplo");

			ArrayList<AppointmentDB> list = new ArrayList<>();

			try {

				list = days.get(i).getAppointments();

			}catch(IndexOutOfBoundsException e){

				e.printStackTrace();
			}

			DayType type = DayType.NORMAL;

			if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == (i + 1)
				&& Calendar.getInstance().get(Calendar.MONTH) == date.get(Calendar.MONTH)
				&& Calendar.getInstance().get(Calendar.YEAR) == date.get(Calendar.YEAR))
				type = DayType.TODAY;

			CalendarDayComponent dateItem = new CalendarDayComponent(calendar_day, list, type);
	
			dateItem.setPrefSize(200, 200);

			GridPane.setHgrow(dateItem, Priority.NEVER);
			GridPane.setVgrow(dateItem, Priority.NEVER);
	
			dateItem.setOnMouseClicked(e -> {

				current_date = Calendar.getInstance();
				current_date.setTime(date.getTime());
				current_date.set(current_date.get(Calendar.YEAR), current_date.get(Calendar.MONTH), calendar_day);
				HomePage.listCalendar.update(current_date);
			});

			this.add(dateItem, line, column);

			line++;

			if (line > 6) {

				line = 0;
				column++;
			}
		}

		if(line > 0){

			/* Adicionando dias do mes seguinte no calendario */
			for (int i = line; i < 7; i++) {
				
				EventDB event = new EventDB();
				event.setTitulo("mes seguinte");

				ArrayList<AppointmentDB> list = new ArrayList<>();
				
				CalendarDayComponent dateItem = new CalendarDayComponent(i + 1 - line, list, DayType.OTHER_MONTH);

				dateItem.setPrefSize(200, 200);

				GridPane.setHgrow(dateItem, Priority.NEVER);
				GridPane.setVgrow(dateItem, Priority.NEVER);
		
				this.add(dateItem, i, column);
			}
		}
	}

	public Calendar getDate() {
		return this.date;
	}
}
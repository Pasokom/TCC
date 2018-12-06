package component.homepage;

import java.util.Calendar;

import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import statics.Enums;

public class CalendarComponent extends GridPane {

	Calendar current_date;

	public CalendarComponent(Calendar date) {

		this.getStylesheets().add(this.getClass().getResource("/css/calendar_component.css").toExternalForm());
		this.setPadding(new Insets(10));

		createCalendar(date);
	}

	public void createCalendar(Calendar date) {

		this.getChildren().clear();

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

			GridPane.setHgrow(box, Priority.ALWAYS);
			GridPane.setVgrow(box, Priority.ALWAYS);

			box.setOnMouseClicked(e -> {

				current_date = Calendar.getInstance();
				current_date.setTime(date.getTime());
				current_date.set(current_date.get(Calendar.YEAR), current_date.get(Calendar.MONTH), day);
				HomePage.listCalendar.update(current_date);
			});

			this.add(box, aux1, aux2);

			aux1++;

			if (aux1 > 6) {

				aux1 = 0;
				aux2++;
			}
		}
	}
}
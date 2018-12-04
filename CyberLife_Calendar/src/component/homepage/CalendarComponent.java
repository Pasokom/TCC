package component.homepage;

import java.util.Calendar;

import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

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

		date.add(Calendar.DATE, -date.get(Calendar.DATE) + 1);
		int aux1 = date.get(Calendar.DAY_OF_WEEK);
		int aux2 = 0;
		int aux3 = date.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i = 1; i <= aux3; i++) {

			final int day = i;

			Label lblDay = new Label(String.valueOf(i));

			if (lblDay.getText().equals(String.valueOf(today.get(Calendar.DATE)))
					&& date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
					&& date.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {

				lblDay.setId("today");
			}

			VBox box = new VBox();
			box.getChildren().add(lblDay);

			GridPane.setHgrow(box, Priority.ALWAYS);
			GridPane.setVgrow(box, Priority.ALWAYS);

			box.setOnMouseClicked(e -> {

				current_date = Calendar.getInstance();
				current_date.setTime(date.getTime());
				current_date.set(current_date.get(Calendar.YEAR), current_date.get(Calendar.MONTH), day);
				HomePage.listCalendar.update(current_date);
			});

			this.add(box, aux1 - 1, aux2);

			aux1++;

			if (aux1 > 7) {

				aux1 = 1;
				aux2++;
			}
		}
	}
}
package component.homepage;

import java.util.Calendar;

import display.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CalendarComponent extends GridPane {
	
	public CalendarComponent() {
	
		this.getStylesheets().add(this.getClass().getResource("/css/calendar_component.css").toExternalForm());
		
		this.setPadding(new Insets(10));
		
		Calendar date = Calendar.getInstance();
		createCalendar(date);
	}
	
	public void createCalendar(Calendar date) {
		
		Calendar today = Calendar.getInstance();
		
		date.add(Calendar.DATE, -date.get(Calendar.DATE) + 1);
		int aux1 = date.get(Calendar.DAY_OF_WEEK);
		int aux2 = 0;
		int aux3 = date.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		for(int i = 1; i <= aux3; i++) {
			
			final int day = i;
			
			Label lblDay = new Label(String.valueOf(i));
			
			if(lblDay.getText().equals(String.valueOf(today.get(Calendar.DATE)))) {
				lblDay.setId("today");
			}
			
			VBox box = new VBox();
			box.getChildren().add(lblDay);
			
			GridPane.setHgrow(box, Priority.ALWAYS);
			GridPane.setVgrow(box, Priority.ALWAYS);
			
			box.setOnMouseClicked(e ->{
				
				Calendar dCalendar = Calendar.getInstance();
				dCalendar.set(dCalendar.get(Calendar.YEAR), dCalendar.get(Calendar.MONTH), day);
				HomePage.reminderList.update(dCalendar);
			});
			
			this.add(box, aux1 - 1, aux2);
			
			aux1++;
			
			if(aux1 > 7) {
				
				aux1 = 1;
				aux2++;
			}
		}		
	}
}

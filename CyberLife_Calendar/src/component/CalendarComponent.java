package component;

import java.util.Calendar;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CalendarComponent extends GridPane {
	
	public CalendarComponent() {
		
		this.setPadding(new Insets(10));
		
		Calendar date = Calendar.getInstance();
		date.set(2018, 10, 28);
		createCalendar(date);
	}
	
	public void createCalendar(Calendar date) {
		
		date.add(Calendar.DATE, -date.get(Calendar.DATE) + 1);
		int aux1 = date.get(Calendar.DAY_OF_WEEK);
		int aux2 = 0;
		
		for(int i = 1; i < 31; i++) {
			
			Label lblDay = new Label(String.valueOf(i));
			
			VBox box = new VBox();
			box.getChildren().add(lblDay);
			
			GridPane.setHgrow(box, Priority.ALWAYS);
			GridPane.setVgrow(box, Priority.ALWAYS);
			this.add(box, aux1 - 1, aux2);
			
			aux1++;
			
			if(aux1 > 7) {
				
				aux1 = 1;
				aux2++;
			}
		}		
	}
}

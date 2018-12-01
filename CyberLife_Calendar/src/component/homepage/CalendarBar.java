package component.homepage;

import java.util.Calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import statics.Enums;

public class CalendarBar extends HBox {

	Label lblMonth;
	
	public CalendarBar() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());
		this.setId("header");
		
		Calendar date = Calendar.getInstance();
		
		lblMonth = new Label(Enums.Month.values()[date.get(Calendar.MONTH)].getValue());
		lblMonth.setFont(new Font(30));
		
		this.getChildren().addAll(lblMonth);
	}
}

package component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class CalendarBar extends HBox {

	Label lblMonth;
	
	public CalendarBar() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());
		this.setId("header");
		
		lblMonth = new Label("Novembro");
		lblMonth.setFont(new Font(30));
		
		this.getChildren().addAll(lblMonth);
	}
}

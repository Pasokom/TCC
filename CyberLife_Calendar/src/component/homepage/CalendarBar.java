package component.homepage;

import java.util.Calendar;

import display.HomePage;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import statics.Enums;

public class CalendarBar extends HBox {

	Label lblMonth;
	Label lblLeftArrow, lblRightArrow;
	Calendar date;
	
	public CalendarBar(Calendar date) {
		this.date = date;
		
		this.getStylesheets().add(this.getClass().getResource("/css/calendar_bar.css").toExternalForm());
		this.setId("header");
		this.setMaxHeight(this.heightProperty().get());
		
		lblMonth = new Label(Enums.Month.values()[date.get(Calendar.MONTH)].getValue() + " - " + date.get(Calendar.YEAR));
		lblMonth.setFont(new Font(30));
		lblMonth.prefWidthProperty().bind(this.widthProperty());
		
		lblLeftArrow = new Label();
		lblLeftArrow.setId("control_left");
		lblLeftArrow.prefHeightProperty().bind(this.heightProperty());
		lblRightArrow = new Label();
		lblRightArrow.setId("control_right");
		lblRightArrow.prefHeightProperty().bind(this.heightProperty());
		
		this.setSpacing(15);
		this.getChildren().addAll(lblMonth, lblLeftArrow, lblRightArrow);
		
		HomePage.calendarComponent = new CalendarComponent(date);
		
		lblLeftArrow.setOnMouseClicked(e ->{
			this.date.add(Calendar.MONTH, -1);
			lblMonth.setText(Enums.Month.values()[this.date.get(Calendar.MONTH)].getValue() + " - " + date.get(Calendar.YEAR));
			HomePage.calendarComponent.createCalendar(this.date);
		});
		
		lblRightArrow.setOnMouseClicked(e ->{
			this.date.add(Calendar.MONTH, 1);
			lblMonth.setText(Enums.Month.values()[this.date.get(Calendar.MONTH)].getValue() + " - " + date.get(Calendar.YEAR));
			HomePage.calendarComponent.createCalendar(this.date);
		});
	}
}

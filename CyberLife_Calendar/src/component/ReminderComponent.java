package component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ReminderComponent extends VBox{

	public Label lblDay, lblHour, lblReminderTitle;
		
	public ReminderComponent() { 
		
		this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());
		this.setId("this");
		
		this.lblDay = new Label();
		this.lblHour = new Label();
		this.lblHour.setId("hora");
		this.lblReminderTitle = new Label();
				
		HBox card = new HBox();
		card.getChildren().addAll(lblReminderTitle, lblHour);
		card.setId("card");
		
		this.getChildren().add(card);
	}
}
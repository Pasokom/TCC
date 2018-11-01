package component;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ReminderComponent extends HBox{

	public Label lblDay, lblHour, lblReminderTitle;
	
	private Button bntDelete;
	
	public ReminderComponent() { 
		
		this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());
		
		this.lblDay = new Label();
		this.lblHour = new Label();
		this.lblReminderTitle = new Label();
		
		this.bntDelete = new Button("falta o icone");
		
		VBox vLabels=  new VBox();
		
		vLabels.getChildren().add(lblReminderTitle);
		vLabels.getChildren().addAll(lblDay, lblHour);
		
//		vLabels.setAlignment(Pos.CENTER);
//		this.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		this.getChildren().add(vLabels);
		this.getChildren().add(this.bntDelete);	
	}
}
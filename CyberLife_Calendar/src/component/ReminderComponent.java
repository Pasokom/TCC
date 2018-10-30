package component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ReminderComponent extends HBox{

	
	
	private Label lblDay, lblHour, lblReminderTitle;
	private Button bntDelete;
	
	
	public ReminderComponent() { 
		
		this.lblDay = new Label();
		this.lblHour = new Label();
		this.lblReminderTitle = new Label();
			
		
		this.bntDelete = new Button("COLOCA O ICONE AQUI");
		
		
		
		this.getChildren().addAll(lblReminderTitle, lblDay, lblHour);
	}
}
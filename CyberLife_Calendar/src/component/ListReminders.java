package component;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ListReminders extends VBox{

	
	private Label lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	
	public ListReminders () { 
		
		this.lblReminder = new Label("TEST");
		
		this.vContent = new VBox();
		this.listReminder =  new CustomScroll();
		
		listReminder.setComponent(vContent);
		
		for ( int i = 0 ; i < 20 ; i ++) { 
			vContent.getChildren().add(new Label("teste"));
		}
		
		
		
		this.getChildren().add(lblReminder);
		this.getChildren().add(vContent);
	
	}
}

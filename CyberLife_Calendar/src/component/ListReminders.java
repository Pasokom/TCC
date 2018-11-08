package component;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListReminders extends VBox{

	
	private Label lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	private HBox hButtons;
	private Button btnAddReminder;
	
	public ListReminders () { 
		
		this.setStyle("-fx-background-color: #DEDEDE");
		
		this.lblReminder = new Label("Programa��o");
		
		this.vContent = new VBox();
		this.listReminder =  new CustomScroll();
		
		this.hButtons=new HBox();
		hButtons.setAlignment(Pos.CENTER_RIGHT);
		this.btnAddReminder=new  Button("icode de + ");
		
		hButtons.getChildren().add(btnAddReminder);
		
		listReminder.setComponent(vContent);
		
		for ( int i = 0 ; i < 10 ; i ++) { 
			
			ReminderComponent rc = new ReminderComponent();
			rc.lblDay.setText("segunda");
			rc.lblHour.setText("17:20");
			rc.lblReminderTitle.setText("Limpar o quarto");
			
			vContent.getChildren().add(rc);
		}
		
		
		this.setAlignment(Pos.CENTER);
		
		
		this.getChildren().add(lblReminder);
		this.getChildren().add(listReminder);
		this.getChildren().add(hButtons);
	}
	
	public void addReminder() {
		
		vContent.getChildren().add(new ReminderComponent());
	}
}

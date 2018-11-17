package component.reminder;

import java.util.ArrayList;

import component.CustomScroll;
import component.ReminderComponent;
import db.functions.RetrieveReminders;
import db.pojo.ReminderBanco;
import display.Reminder;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

public class ListReminders extends VBox{

	
	private Label lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	private HBox hButtons;
	private Button btnAddReminder;
	
	public ListReminders () { 
		
		this.setStyle("-fx-background-color: #DEDEDE");
		
		this.lblReminder = new Label("Programação");
		
		this.vContent = new VBox();
		this.listReminder =  new CustomScroll();
		
		this.hButtons=new HBox();
		hButtons.setAlignment(Pos.CENTER_RIGHT);
		this.btnAddReminder = new Button("+");
		this.btnAddReminder.setOnAction(e -> {
<<<<<<< HEAD
=======
			Main.main_stage.setScene(new Reminder());
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
		});
		
		hButtons.getChildren().add(btnAddReminder);
		
		listReminder.setComponent(vContent);
		
<<<<<<< HEAD
		for ( int i = 0 ; i < 5 ; i ++) { 
			
			ReminderComponent rc = new ReminderComponent();
			rc.lblDay.setText("segunda");
			rc.lblHour.setText("17:20");
			rc.lblReminderTitle.setText("Limpar o quarto");
			
			vContent.getChildren().add(rc);
		}
=======
		addReminders();
		
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
		this.setAlignment(Pos.CENTER);
		
		this.getChildren().add(lblReminder);
		this.getChildren().add(listReminder);
		this.getChildren().add(hButtons);
	}
	
	public void addReminders() {
		
		RetrieveReminders reminders = new RetrieveReminders();
		ArrayList<ReminderBanco> lista = reminders.getReminders();
		
		for (ReminderBanco reminderBanco : lista) {
			
			ReminderComponent rc = new ReminderComponent();
			rc.lblReminderTitle.setText(reminderBanco.getTitulo());
			
			vContent.getChildren().add(rc);
		}
	}
}

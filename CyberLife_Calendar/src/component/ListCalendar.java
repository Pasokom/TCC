package component;

import java.util.ArrayList;

import component.CustomScroll;
import component.EventComponent;
import component.ReminderComponent;
import db.functions.RetrieveEvents;
import db.functions.RetrieveReminders;
import db.pojo.EventDB;
import db.pojo.ReminderBanco;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ListCalendar extends VBox{

	private Label lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	
	RetrieveEvents retrieveEvents = new RetrieveEvents();
	
	public ListCalendar () { 
		
		this.setStyle("-fx-background-color: #DEDEDE");
		
		this.lblReminder = new Label("Programação");
		
		this.vContent = new VBox();
		vContent.setPadding(new Insets(10));
		vContent.setSpacing(10);
		this.listReminder =  new CustomScroll();
		
		listReminder.setComponent(vContent);
		
		this.getChildren().add(lblReminder);
		this.getChildren().add(listReminder);
		
		addEvents();
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
	
	public void addEvents() {
		retrieveEvents.updateList();
		
		for(EventDB event : RetrieveEvents.listEvents) {		
			EventComponent eC = new EventComponent();
			
			eC.getLbl_titulo().setText(event.getTitulo());
			eC.getLbl_hora().setText(event.getData_inicio().toString());
			
			this.vContent.getChildren().add(eC);
		}
	}
}

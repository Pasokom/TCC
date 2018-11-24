package component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import component.CustomScroll;
import component.EventComponent;
import component.ReminderComponent;
import db.functions.RetrieveEvents;
import db.functions.RetrieveReminders;
import db.pojo.EventDB;
import db.pojo.ReminderBanco;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ListCalendar extends VBox{

	private Label lblSelectedDate, lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	private ArrayList<VBox> hours;
	
	RetrieveEvents retrieveEvents = new RetrieveEvents();
	
	public ListCalendar () { 
		
		this.prefWidthProperty().set(250);
		this.setId("this");
		
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
	
		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());
		
		/* Cabeçario da programação */
		HBox hHeader = new HBox();
		hHeader.setId("header");
		
		this.lblSelectedDate = new Label(calendar.get(Calendar.DAY_OF_MONTH)
				+ "/" + (calendar.get(Calendar.MONTH) + 1));
		lblSelectedDate.setFont(new Font(30));
		
		this.lblReminder = new Label(" - Programação");

		hHeader.setAlignment(Pos.CENTER_LEFT);
		
		hHeader.getChildren().addAll(lblSelectedDate, lblReminder);
		/* Fim do cabeçario da programação */
		
		hours = new ArrayList<>();
		addHours();
		
		this.vContent = new VBox();
		this.vContent.setId("content");
		vContent.setPadding(new Insets(10,0,10,10));
		vContent.setSpacing(10);
		vContent.getChildren().addAll(hours);
		
		this.listReminder =  new CustomScroll();
		
		vContent.prefWidthProperty().bind(listReminder.widthProperty());
		
		listReminder.setComponent(vContent);
		listReminder.setId("list");
		
		this.getChildren().add(hHeader);
		this.getChildren().add(listReminder);
		
		addEvents();
	}
	
	public void addHours() {
		
		for(int i = 0; i < 24; i++) {
			
			VBox hour = new VBox();
			hour.setSpacing(5);
			
			Label lblHour = new Label(i+":00");
			
			VBox hourContent = new VBox();
			hourContent.setSpacing(5);
			
			Separator separator = new Separator();
			
			hour.getChildren().addAll(lblHour, hourContent, separator);
			
			this.hours.add(hour);
		}
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
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(event.getData_inicio());
			
			eC.getLbl_titulo().setText(event.getTitulo());
			eC.getLbl_hora().setText(" - " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)));
			
			calendar.setTime(event.getData_inicio());
			
			((VBox)((VBox)this.vContent.getChildren().get(calendar.get(Calendar.HOUR_OF_DAY))).getChildren().get(1)).getChildren().add(eC);
		}
	}
}

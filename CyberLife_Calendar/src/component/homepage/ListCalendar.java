package component.homepage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import component.CustomScroll;
import component.EventComponent;
import db.functions.LoadReminder;
import db.functions.RetrieveEvents;
import db.functions.RetrieveReminders;
import db.pojo.EventDB;
import db.pojo.ReminderDB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import statics.SESSION;

public class ListCalendar extends VBox{

	private Calendar date;
	private Label lblSelectedDate, lblReminder;
	private VBox vContent;
	private CustomScroll listReminder;
	private ArrayList<VBox> hours;
	
	RetrieveEvents retrieveEvents = new RetrieveEvents();
	LoadReminder loadReminders;
	
	public ListCalendar (Calendar date) { 
		
		this.date = date;
		
		this.prefWidthProperty().set(250);
		this.setId("this");
	
		this.getStylesheets().add(this.getClass().getResource("/css/list_calendar.css").toExternalForm());
		
		/* Cabeçario da programação */
		HBox hHeader = new HBox();
		hHeader.setId("header");
		
		this.lblSelectedDate = new Label(date.get(Calendar.DAY_OF_MONTH)
				+ "/" + (date.get(Calendar.MONTH) + 1));
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
		
		addEvents(this.date);
		addReminders();
	}
	
	private void addHours() {
		
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
	
	private void addReminders() {
		
		ArrayList<ReminderDB> reminders = new ArrayList<>();
		
		try {
			loadReminders = new LoadReminder();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			reminders = loadReminders.getReminderForToday((int)SESSION.get_user_cod(), LoadReminder.TypeOfQuery.ALL_REMINDERS);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for(ReminderDB reminder : reminders) {
//			
//			System.out.println("reminder loaded " + reminder.getTitle());
//			
//		}
		
	}
	
	private void addEvents(Calendar date) {
		retrieveEvents.updateList();
		
		for(EventDB event : RetrieveEvents.listEvents) {
			
			Date eventDate = new Date(event.getData_inicio().getTime());
			
			LocalDate myDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate myEventDate = eventDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			if(myDate.compareTo(myEventDate) == 0) {
				
				EventComponent eC = new EventComponent(event);
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(event.getData_inicio());
				
				eC.getLbl_titulo().setText(event.getTitulo());
				eC.getLbl_hora().setText(" - " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)));
				
				calendar.setTime(event.getData_inicio());
				
				((VBox)((VBox)this.vContent.getChildren().get(calendar.get(Calendar.HOUR_OF_DAY))).getChildren().get(1)).getChildren().add(eC);
			}
		}
	}
	
	public void update(Calendar date) {
		
		lblSelectedDate.setText(date.get(Calendar.DAY_OF_MONTH)
				+ "/" + (date.get(Calendar.MONTH) + 1));
		
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
		
		this.getChildren().set(1, listReminder);
		
		addEvents(date);
	}
}

package component.appointment;

import db.pojo.HolidayDB;
import db.pojo.eventPOJO.EventDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class AppointmentMini extends HBox {

    private Label lbl_titulo;

    public AppointmentMini() {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_mini.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label("???");
        lbl_titulo.setId("titulo");

        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.getStyleClass().add("hbox");
        card.setId("demo_card");
        
        this.getChildren().add(card);
    }

    public AppointmentMini(ReminderDB reminder) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_mini.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(reminder.getTitulo());
        lbl_titulo.setId("titulo");
        
        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.getStyleClass().add("hbox");
        card.setId("reminder");

        this.getChildren().add(card);
    }

    public AppointmentMini(EventDB event) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_mini.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(event.getTitulo());
        lbl_titulo.setId("titulo");
        
        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.getStyleClass().add("hbox");
        card.setId("event");

        this.getChildren().add(card);
    }

    public AppointmentMini(HolidayDB holiday) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_mini.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(holiday.getNome());
        lbl_titulo.setId("titulo");
        
        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.getStyleClass().add("hbox");
        card.setId("holiday");

        this.getChildren().add(card);
    }

    public AppointmentMini(TarefaDB task) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_mini.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(task.getNome_tarefa());
        lbl_titulo.setId("titulo");
        
        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.getStyleClass().add("hbox");
        card.setId("task");

        this.getChildren().add(card);
    }
}
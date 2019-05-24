package component.appointment;

import java.util.Calendar;

import db.functions.appointment.LoadAppointment;
import db.pojo.HolidayDB;
import db.pojo.Moon;
import db.pojo.eventPOJO.EventDB;
import db.pojo.projectPOJO.TarefaDB;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AppointmentComponent extends VBox{

    private Label lbl_titulo;
    private Label sup_title;

    private LoadAppointment load;

    public AppointmentComponent() {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label("???");
        lbl_titulo.setId("titulo");

        HBox card = new HBox();
        card.getChildren().add(lbl_titulo);
        card.setId("card");
        
        this.getChildren().add(card);
    }

    public AppointmentComponent(EventDB event) {
        
        load = new LoadAppointment();

        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(event.getTitulo());
        lbl_titulo.setId("titulo");

        Calendar start_time = Calendar.getInstance();
        start_time.setTime(event.getData_inicio());
        
        Calendar end_time = Calendar.getInstance();
        end_time.setTime(event.getData_fim());

        sup_title = new Label(
            start_time.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", start_time.get(Calendar.MINUTE)));
            sup_title.setId("above_info");

        if(start_time.compareTo(end_time) != 0){
            sup_title.setText(sup_title.getText() + " - " + end_time.get(Calendar.HOUR_OF_DAY) 
            + ":" + String.format("%02d", end_time.get(Calendar.MINUTE)));
        }

        VBox card = new VBox();

        if(!event.isDia_todo())
            card.getChildren().add(sup_title);

        card.getChildren().add(lbl_titulo);
        card.setId("event_card");

        this.getChildren().add(card);

        this.setOnMouseClicked(e -> {

            EventDB c_event = load.loadEvent(event.getCod_evento(), event);
            AppointmentInfo info = new AppointmentInfo(c_event);

            Point2D point2d = this.localToScreen(0d, 0d);

            info.show(this.getScene().getWindow());

            info.setX(point2d.getX() + this.getWidth() + 5);
            info.setY(point2d.getY() + (this.getHeight() / 2) - (info.getHeight() / 2));
        });
    }

    public AppointmentComponent(ReminderDB reminder) {
        
        load = new LoadAppointment();

        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(reminder.getTitulo());
        lbl_titulo.setId("titulo");

        Calendar start_time = Calendar.getInstance();
        start_time.setTime(reminder.getHorario());

        sup_title = new Label(
            start_time.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", start_time.get(Calendar.MINUTE)));
            sup_title.setId("above_info");

        VBox card = new VBox();

        if(reminder.isConcluido()){

            Text txt_titulo = new Text();
            txt_titulo.setText(reminder.getTitulo());
            txt_titulo.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
            txt_titulo.setFill(Color.WHITE);
            txt_titulo.setStyle("-fx-strikethrough: true;");
            card.getChildren().add(txt_titulo);
        }
        else{

            if(!reminder.isDia_todo())
                card.getChildren().add(sup_title);

            card.getChildren().add(lbl_titulo);
        }

        card.setId("reminder_card");

        this.getChildren().add(card);

        this.setOnMouseClicked(e -> {

            ReminderDB c_reminder = load.loadReminder(reminder.getCod_lembrete(), reminder);
            AppointmentInfo info = new AppointmentInfo(c_reminder);

            Point2D point2d = this.localToScreen(0d, 0d);

            info.show(this.getScene().getWindow());

            info.setX(point2d.getX() + this.getWidth() + 5);
            info.setY(point2d.getY() + (this.getHeight() / 2) - (info.getHeight() / 2));
        });
    }

    public AppointmentComponent(HolidayDB holiday) {
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(holiday.getNome());
        lbl_titulo.setId("titulo");

        sup_title = new Label("Feriado");
        sup_title.setId("above_info");

        VBox card = new VBox();
        card.getChildren().addAll(sup_title, lbl_titulo);
        card.setId("holiday_card");

        this.getChildren().add(card);
    }

    public AppointmentComponent(Moon moon){
        
        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(moon.getDescription());
        lbl_titulo.setId("titulo");

        ImageView img_moon = new ImageView();
        img_moon.setPreserveRatio(true);
        img_moon.setFitWidth(30);
        img_moon.setId("moon-" + moon.getPhase());

        HBox card = new HBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setSpacing(10);
        card.getChildren().addAll(img_moon, lbl_titulo);
        card.setId("moon_card");

        this.getChildren().add(card);
    }

    public AppointmentComponent(TarefaDB task) {

        this.getStylesheets().add(this.getClass().getResource("/css/appointment_component.css").toExternalForm());
		this.setId("this");

        lbl_titulo = new Label(task.getNome_tarefa());
        lbl_titulo.setId("titulo");

        VBox card = new VBox();

        if (task.getData_inicio() != null) {

            Calendar start_time = Calendar.getInstance();
            start_time.setTime(task.getData_inicio());
    
            sup_title = new Label(
                start_time.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", start_time.get(Calendar.MINUTE)));
            sup_title.setId("above_info");
            card.getChildren().add(sup_title);
        }

        card.getChildren().add(lbl_titulo);
        card.setId("task_card");

        this.getChildren().add(card);
    }
}
package component.appointment;

import java.text.SimpleDateFormat;
import java.util.Date;

import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * AppointmentInfo
 */
public class AppointmentInfo extends Scene {

    Label lbl_horario;
    Label lbl_titulo;

    Label lbl_place;
    Label lbl_description;
    Label lbl_repetition; 

    Button btn_delete;
    Button btn_done;

    public AppointmentInfo(ReminderDB reminder) {
        super(new VBox());

        AnchorPane root = new AnchorPane();

        VBox vb_content = new VBox();
        
        /* horario */
        Date date = new Date();
        date.setTime(reminder.getHorario().getTime());
        String formattedDate;

        if(reminder.isDia_todo())
            formattedDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        else
            formattedDate = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(date);

        lbl_horario = new Label(formattedDate);

        /* titulo */
        lbl_titulo = new Label(reminder.getTitulo());
    }
}
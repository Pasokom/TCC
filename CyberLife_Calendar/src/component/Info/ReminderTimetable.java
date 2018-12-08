package component.Info;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.pojo.reminderPOJO.ReminderSchedule;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import statics.Enums.RepetitionType;

/**
 * @author Manoel
 * 
 * classe responsavel por mostrar os horários do lembretes
 */
public class ReminderTimetable extends HBox {

    Label lblTime;

    public ReminderTimetable(ArrayList<ReminderSchedule> schedules, RepetitionType type) {
    
        this.setSpacing(10);

        lblTime = new Label();
        Format formatter = new SimpleDateFormat("HH:mm");

        switch (type) {
            case ALL_DAY:
                lblTime.setText("O dia inteiro");
                this.getChildren().add(lblTime);
                break;

            case INTERVAL:
                String text = "";
                text += "De " + formatter.format(schedules.get(0).getDatetime_begin());
                text += " as " +  schedules.get(0).getTimeEnd().toString().substring(0,5);
                text += " a cada " + schedules.get(0).getMinutesInterval() + " minutos";
                lblTime.setText(text);
                this.getChildren().add(lblTime);
                break;

            case TIME_PICKER:
                for (ReminderSchedule schedule : schedules) {
                
                    lblTime = new Label(formatter.format(schedule.getDatetime_begin()));
                    this.getChildren().add(lblTime);
                }
                break;

            default:
                lblTime.setText("");
                break;
        }
    }
}
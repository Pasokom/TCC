package component.reminder;

import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * ReminderComponentDemo
 */
public class ReminderComponentDemo extends HBox {

    private Label lblTitulo;

    public ReminderComponentDemo(ReminderDB reminder) {
    
        this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());

        lblTitulo = new Label(reminder.getTitle());
        lblTitulo.prefWidthProperty().bind(this.widthProperty());
        lblTitulo.setId("demo_card");

        this.getChildren().add(lblTitulo);
    }
}
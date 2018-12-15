package component.reminder;

import java.util.ArrayList;
import component.Info.RecurrenceInfo;
import component.Info.ReminderTimetable;
import db.pojo.reminderPOJO.ReminderDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.Enums.RepetitionType;

public class ReminderInfo extends Stage {

	private Label lblTitulo;
	private ReminderTimetable rTimetable;
	private RecurrenceInfo rInfo;
	private Label segunda;
	private Label terca;
	private Label quarta;
	private Label quinta;
	private Label sexta;
	private Label sabado;
	private Label domingo;

	public ReminderInfo(ReminderDB reminder) {

		this.initStyle(StageStyle.UNDECORATED);

		GridPane gp = new GridPane();

		Scene scene = new Scene(gp);
		this.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/EventInfo.css").toExternalForm());
		gp.setId("this");

		segunda = new Label("S");
		terca = new Label("T");
		quarta = new Label("Q");
		quinta = new Label("Q");
		sexta = new Label("S");
		sabado = new Label("S");
		domingo = new Label("D");

		ArrayList<Label> lblDaysOfWeek = new ArrayList<>();

		lblDaysOfWeek.add(domingo);
		lblDaysOfWeek.add(segunda);
		lblDaysOfWeek.add(terca);
		lblDaysOfWeek.add(quarta);
		lblDaysOfWeek.add(quinta);
		lblDaysOfWeek.add(sexta);
		lblDaysOfWeek.add(sabado);

		lblTitulo = new Label("T�tulo: " + reminder.getTitle().toString());
		rTimetable = new ReminderTimetable(reminder.getlReminderSchedule(),
				RepetitionType.values()[reminder.getRepetitionType()]);
		rInfo = new RecurrenceInfo(reminder);

		gp.add(lblTitulo, 0, 0);

		if (!rInfo.getText().equals(""))
			gp.add(rInfo, 0, 1);

		gp.add(rTimetable, 0, 2);

		this.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					close();
				}
			}
		});
	}
}
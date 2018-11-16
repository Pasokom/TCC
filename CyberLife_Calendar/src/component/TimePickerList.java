package component;

import java.util.ArrayList;

import component.reminder.TimePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimePickerList extends HBox {

	private Label lblTime;
	private Button btnAddTime;

	private ArrayList<String> list_values;

	public TimePickerList() {
		this.list_values = new ArrayList<>();
		lblTime = new Label("Horarios:");

		HBox horas = new HBox();
		horas.setId("hbHoras");

		btnAddTime = new Button("+");
		btnAddTime.setOnAction(evento -> {

			if (horas.getChildren().size() < 5) {

				TimePicker tp = new TimePicker(true);

				tp.set_event_ok(e -> {
					System.out.println("[CONFIRMATION] it's working");
					tp.change_label();
					add_value(tp.get_value());
					tp.close_stage();
				});
				horas.getChildren().add(tp);
			}
		});

		
		horas.getChildren().add(new TimePicker(true));
		
		this.setSpacing(10);
		this.getChildren().addAll(lblTime, horas, btnAddTime);
	}

	public ArrayList<String> get_selected_time() {
		return list_values;
	}
	public void add_value(String value) {
		this.list_values.add(value);
	}

}

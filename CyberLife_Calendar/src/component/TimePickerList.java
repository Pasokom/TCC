package component;

import java.util.ArrayList;

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

				TimePicker tp = new TimePicker();
				/** 
				 */
				tp.set_event_ok(e -> {
					System.out.println("[COFNIRMATION] its working");
//					System.out.println(tp.get_value());
					tp.change_label();
					add_value(tp.get_value());
					tp.close_stage();
				});
				horas.getChildren().add(tp);
			}
		});

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

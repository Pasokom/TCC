package component;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimePickerList extends HBox {
	
	private Label lblTime;
	private Button btnAddTime;
	
	public TimePickerList() {
		
		lblTime = new Label("Horarios:");
		
		HBox horas = new HBox();
		horas.setId("hbHoras");
		
		btnAddTime = new Button("+");
		btnAddTime.setOnAction(evento -> {
			
			if(horas.getChildren().size() < 5) {
				
				horas.getChildren().add(new TimePicker());
			}
		});
		
		horas.getChildren().add(new TimePicker());
		
		this.setSpacing(10);
		this.getChildren().addAll(lblTime, horas, btnAddTime);
	}

}

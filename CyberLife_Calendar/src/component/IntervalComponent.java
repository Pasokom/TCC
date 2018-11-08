package component;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class IntervalComponent extends HBox {

	private Label lblFrom, lblUntil, lblEach;
	private TimePicker timeStart, timeEnd;
	private TextField txtTime;
	private ChoiceBox<String> cbxTime;
	
	public IntervalComponent() {
		
		lblFrom = new Label("De");
		timeStart = new TimePicker(false);
		lblUntil = new Label("até");
		timeEnd = new TimePicker(false);
		lblEach = new Label("a cada");
		txtTime = new TextField();
		txtTime.setPrefWidth(50);
		cbxTime = new ChoiceBox<>();
		cbxTime.setItems(FXCollections.observableArrayList("Minutos", "Horas"));
		cbxTime.getSelectionModel().select(0);
		
		this.setSpacing(10);
		this.getChildren().addAll(lblFrom, timeStart, lblUntil, timeEnd, lblEach, txtTime, cbxTime);
	}
}

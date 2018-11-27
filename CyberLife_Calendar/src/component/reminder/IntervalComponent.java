package component.reminder;

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

	/**
	 * retorna o valor escolhido pelo usuario o valor de retorno sempre será em
	 * minutos
	 */
	public int selected_interval() {
		boolean is_minute_selected = cbxTime.getSelectionModel().getSelectedIndex() == 0;
		if (is_minute_selected)
			return Integer.valueOf(this.txtTime.getText());
		return Integer.valueOf(txtTime.getText()) * 60;
	}
	
	/*
	 * funções para retornar o horario de inicio e o de fim 
	 */
	public String start_time() {
		return this.timeStart.get_value();
	}

	public String end_time() {
		return this.timeEnd.get_value();
	}

}






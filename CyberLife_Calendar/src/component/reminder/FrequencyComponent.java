package component.reminder;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import statics.Enums;

public class FrequencyComponent extends HBox {

	ChoiceBox<String> repeatOptions;
	Spinner<Integer> repeat;

	public FrequencyComponent() {
		repeatOptions = new ChoiceBox<>();
		// populando a caixa de escolha
		repeatOptions.setItems(FXCollections.observableArrayList("dia", "semana", "mês", "ano"));
		// repeatOptions.getSelectionModel().select(1); // definindo o segundo item da lista como o padrao

		// criando spinner de 1 a 100 de 1 em 1'
		SpinnerValueFactory<Integer> repeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);

		repeat = new Spinner<>();
		repeat.setValueFactory(repeatValueFactory);
		repeat.setPrefWidth(80); // alterando a largura

		this.setSpacing(10);
		this.getChildren().addAll(repeatOptions, repeat);
	}

	public int get_choosed_value() {
		return this.repeat.getValue();
	}

	public void set_choosed_value(int value) {
		repeat.getValueFactory().setValue(value);
	}

	public void setSelected(int index) {
		repeatOptions.getSelectionModel().select(index);
	}

	/*
	 * retorna o tipo de recorrencia selecionado
	 */
	public int get_selected_option() {
		switch (repeatOptions.getSelectionModel().getSelectedItem()) {
		case "mês":
			return Enums.TypeRecurrence.MONTHLY.getValue();
		case "dia":
			return Enums.TypeRecurrence.DAYLY.getValue();
		case "semana":
			return Enums.TypeRecurrence.WEEKLY.getValue();
		case "ano":
			return Enums.TypeRecurrence.YEARLY.getValue();
		}
		return 0;
	}

}

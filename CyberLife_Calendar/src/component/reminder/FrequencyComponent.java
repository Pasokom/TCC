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
		//populando a caixa de escolha
		repeatOptions.setItems(FXCollections.observableArrayList(
				"dia", "semana", "mês", "ano")
			);
		repeatOptions.getSelectionModel().select(1); //definindo o segundo item da lista como o padrao
		
		//criando spinner de 1 a 100 de 1 em 1
		SpinnerValueFactory<Integer> repeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		
		repeat = new Spinner<>();
		repeat.setValueFactory(repeatValueFactory);
		repeat.setPrefWidth(80); //alterando a largura
		
		this.setSpacing(10);
		this.getChildren().addAll(repeatOptions, repeat);
	}
	
	public int get_choosed_value () { 
		return this.repeat.getValue();
	}
	/* 
	 * retorna o tipo de recorrencia selecionado
	 */
	public String get_selected_option() { 
		switch (repeatOptions.getSelectionModel().getSelectedItem()) {
		case "mês":
			return Enums.TypeRecurrence.MONTHLY.get_value();
		case "dia": 
			return Enums.TypeRecurrence.DAYLY.get_value();
		case "semana": 
			return Enums.TypeRecurrence.WEEKLY.get_value();
		case "ano": 
			return Enums.TypeRecurrence.YEARLY.get_value();
		}
		return null;
	}
	
}





















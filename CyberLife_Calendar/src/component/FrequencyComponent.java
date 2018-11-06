package component;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;

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
}

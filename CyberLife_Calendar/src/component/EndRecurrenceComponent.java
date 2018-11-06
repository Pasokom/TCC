package component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EndRecurrenceComponent extends VBox {

	private Label lblEndRepeat, lblOccurrence;
	private Spinner<Integer> spnQtdRepeat;
	private ToggleGroup togEndRepeat;
	private RadioButton radNever, radOn, radAfter;

	
	public EndRecurrenceComponent() {
		
		lblEndRepeat = new Label("Termina");
		
		togEndRepeat = new ToggleGroup();
		
		this.setSpacing(15);
		this.getChildren().addAll(lblEndRepeat, setupRadNever(), setupRadOn(), setupRadAfter());
	}
	
	private RadioButton setupRadNever() {
		
		radNever = new RadioButton("Nunca");
		radNever.setToggleGroup(togEndRepeat);
		radNever.setSelected(true);
		
		return radNever;
	}
	
	private HBox setupRadOn() {
		
		radOn = new RadioButton("Em");
		radOn.setToggleGroup(togEndRepeat);
		
		HBox hbOn = new HBox();
		hbOn.setSpacing(10);
		hbOn.getChildren().addAll(radOn, setupDate());
		
		return hbOn;
	}
	
	private HBox setupRadAfter() {
		
		radAfter = new RadioButton("Após");
		radAfter.setToggleGroup(togEndRepeat);
		
		lblOccurrence = new Label("ocorrência(s)");
	
		HBox hbAfter = new HBox();
		hbAfter.setSpacing(10);
		hbAfter.getChildren().addAll(radAfter, setupSpinner(), lblOccurrence);
		
		return hbAfter;
	}
	
	private DatePicker setupDate() {
		
		Calendar calendar = Calendar.getInstance();
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); //instanciando classe que formata a data em string
		calendar.add(Calendar.MONTH, 1);
		
 		LocalDate localDateRepeat = LocalDate.parse(dateFormater.format(calendar.getTime())); ////criando uma data sem time-zone
		
		return new DatePicker(localDateRepeat); //colocando uma data padrao no componente
	}
	
	private Spinner<Integer> setupSpinner(){
		
		//criando dois spinners de 1 a 100 de 1 em 1
		SpinnerValueFactory<Integer> qtdRepeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		
		spnQtdRepeat = new Spinner<>();
		spnQtdRepeat.setValueFactory(qtdRepeatValueFactory);
		spnQtdRepeat.setPrefWidth(80); //alterando a largura
		
		return spnQtdRepeat;
	}
}

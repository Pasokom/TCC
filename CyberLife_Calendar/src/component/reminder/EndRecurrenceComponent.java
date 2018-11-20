package component.reminder;

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

	private DatePicker date_picker;
	
	
	
	
	public EndRecurrenceComponent() {
		
		lblEndRepeat = new Label("Termina");
		
		togEndRepeat = new ToggleGroup();
		
		radNever = new RadioButton("Nunca");
		radNever.setToggleGroup(togEndRepeat);
		radNever.setSelected(true);
		

		radOn = new RadioButton("Em");
		radOn.setToggleGroup(togEndRepeat);
		
		HBox hbOn = new HBox();
		hbOn.setSpacing(10);

		/* 
		 * tive que fazer isso pra poder usar o time picker
		 */
		Calendar calendar = Calendar.getInstance();
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); //instanciando classe que formata a data em string
		calendar.add(Calendar.MONTH, 1);
		
 		LocalDate localDateRepeat = LocalDate.parse(dateFormater.format(calendar.getTime())); ////criando uma data sem time-zone
		
		this.date_picker = new DatePicker(localDateRepeat); //colocando uma data padrao no componente

		
		hbOn.getChildren().addAll(radOn, date_picker);
		
		radAfter = new RadioButton("Ap�s");
		radAfter.setToggleGroup(togEndRepeat);
		
		lblOccurrence = new Label("ocorr�ncia(s)");

		//criando dois spinners de 1 a 100 de 1 em 1
		SpinnerValueFactory<Integer> qtdRepeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		
		spnQtdRepeat = new Spinner<>();
		spnQtdRepeat.setValueFactory(qtdRepeatValueFactory);
		spnQtdRepeat.setPrefWidth(80); //alterando a largura
		
		
		HBox hbAfter = new HBox();
		hbAfter.setSpacing(10);
		hbAfter.getChildren().addAll(radAfter, spnQtdRepeat, lblOccurrence);

		
		this.setSpacing(15);
		this.getChildren().addAll(lblEndRepeat, radNever, hbOn, hbAfter );
	}
	/* 
	 * para pegar a data selecionada no date picker
	 */
	public String getChoosed_date() {
		return radOn.selectedProperty().get() ?  date_picker.getValue().toString() : new String();
	}
	public boolean is_choosed_by_date () { 
		return radOn.selectedProperty().get();
	}

	public boolean by_amount_selected() { 
		return this.radAfter.selectedProperty().get();
	}
	public int get_amount_repetition( ) { 
		if (radAfter.selectedProperty().get()) { 
			return spnQtdRepeat.getValue();
		}
		return 0;
	}
	public boolean is_never_end_selected() {
		return this.radNever.selectedProperty().get();
	}
}

package component;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Recurrence extends VBox {

	private Label lblRecurrence, lblRepeat;
	private FrequencyComponent frequency;
	private DayOfWeekSelector dayOfWeekSelector;
	public EndRecurrenceComponent endRecurrence;
	
	public Recurrence() {
		
		lblRecurrence = new Label("Recorrência:");
		lblRepeat = new Label("Repetir a cada");
		
		frequency = new FrequencyComponent();
		
		dayOfWeekSelector = new DayOfWeekSelector();
		
		endRecurrence = new EndRecurrenceComponent();
		
		this.setSpacing(15);
		this.getChildren().addAll(lblRecurrence, lblRepeat, frequency, dayOfWeekSelector, endRecurrence);
	}
	
	/**
	 * retorna o tipo de recorrencia 
	 */
	public String get_frequency() { 
		return frequency.get_selected_option();
	}
	/* 
	 * retorna dias selecionados para repetição 
	 */
	public ArrayList<Boolean> get_test() { 
		return this.dayOfWeekSelector.test();
	}
	
	public String get_end_date() { 
		return endRecurrence.getChoosed_date();
	}
	public int get_amount_choosed() { 
		return endRecurrence.get_amount_repetition();
	}
	
	public boolean is_never_selected() { 
		endRecurrence.get_amount_repetition();
		return endRecurrence.is_never_end_selected();
	}
}


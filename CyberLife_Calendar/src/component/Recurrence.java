package component;

import java.util.ArrayList;

import component.reminder.DayOfWeekSelector;
import component.reminder.EndRecurrenceComponent;
import component.reminder.FrequencyComponent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Recurrence extends VBox {

	/* 
	 * já tem isso pronto em outro lugar
	 */
	public enum IntervalMode{
		DIA, SEMANA, MES, ANO;
	}
	
	public enum DaysOfWeek{
		DOM, SEG, TER, QUA, QUI, SEX, SAB;
	}
	
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
	public int get_recurrence_type() { 
		return frequency.get_selected_option();
	}
	
	public int get_recurrence_value () { 
		return frequency.get_choosed_value();
	}
	/* 
	 * retorna dias selecionados para repetição 
	 */
	public ArrayList<Boolean> get_selected_day() { 
		return this.dayOfWeekSelector.selected_day();
	}
	public String get_end_date() { 
		return endRecurrence.getChoosed_date();
	}
	public Boolean on_date()  { 
		return this.endRecurrence.is_choosed_by_date();
	}
	public int get_amount_choosed() { 
		return endRecurrence.get_amount_repetition();
	}
	public boolean is_by_amount() { 
		return endRecurrence.by_amount_selected();
	}
	public boolean is_never_selected() { 
		return endRecurrence.is_never_end_selected();
	}
}














package component;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Recurrence extends VBox {

	public enum IntervalMode{
		DIA, SEMANA, MES, ANO;
	}
	
	public enum DaysOfWeek{
		DOM, SEG, TER, QUA, QUI, SEX, SAB;
	}
	
	private Label lblRecurrence, lblRepeat;
	private FrequencyComponent frequency;
	private DayOfWeekSelector dayOfWeekSelector;
	private EndRecurrenceComponent endRecurrence;
	
	public Recurrence() {
		
		lblRecurrence = new Label("Recorrência:");
		lblRepeat = new Label("Repetir a cada");
		
		frequency = new FrequencyComponent();
		
		dayOfWeekSelector = new DayOfWeekSelector();
		
		endRecurrence = new EndRecurrenceComponent();
		
		this.setSpacing(15);
		this.getChildren().addAll(lblRecurrence, lblRepeat, frequency, dayOfWeekSelector, endRecurrence);
	}
	
	public int getRepeatIntervalQtd() {
		return frequency.getSelectedMode();
	}
	
	public int getRepeatIntervalMode() {
		return frequency.getQtdInterval();
	}
	
//	public int[] getSelectedWeekDays() {
//		dayOfWeekSelector.
//	}
//	
//	public int getEndRecurrenceMode() {
//		
//	}
//	
//	public String getEndRecurrenceDate() {
//		
//	}
//	
//	public int getEndRecurrenceQtd() {
//		
//	}
}

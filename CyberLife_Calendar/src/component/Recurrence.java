package component;

import java.util.ArrayList;

import component.reminder.DayOfWeekSelector;
import component.reminder.EndRecurrenceComponent;
import component.reminder.FrequencyComponent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Componente responsavel por permitir que o usuario configure a repeticao de
 * seus compromissos
 * 
 * @author manoel
 *
 */
public class Recurrence extends VBox {

	public enum DaysOfWeek {
		DOM, SEG, TER, QUA, QUI, SEX, SAB;
	}

	private Label lblRecurrence, lblRepeat;
	private FrequencyComponent frequency;
	private DayOfWeekSelector dayOfWeekSelector;
	public EndRecurrenceComponent endRecurrence;

	public Recurrence() {

		lblRecurrence = new Label("Recorr�ncia:");
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

	public int get_recurrence_value() {
		return frequency.get_choosed_value();
	}

	public void setDay(int day) {
		this.dayOfWeekSelector.setSelect(day);
	}

	public void setTypeFrequency(int index) {
		this.frequency.setSelected(index);
	}

	public void setChoosedValue(int value) {
		this.frequency.set_choosed_value(value);
	}

	public void setSelectionEnd(int index) {
		this.endRecurrence.setSelectedToggle(index);
	}

	public void setSpinnerValue(int value) {
		this.endRecurrence.setSpinnerValue(value);
	}

	public void setDatePickerValue(DatePicker dt) {
		this.endRecurrence.setDatePickerValue(dt);
	}

	/**
	 * retorna dias selecionados para repeticao
	 */
	public ArrayList<Boolean> get_selected_day() {
		return this.dayOfWeekSelector.selected_day();
	}

	public boolean[] get_selected_days() {
		return this.dayOfWeekSelector.selected_days();
	}

	public String get_end_date() {
		return endRecurrence.getChoosed_date();
	}

	public Boolean on_date() {
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

	public int getSelectedEnd() {
		return endRecurrence.getSelectedEnd();
	}
}

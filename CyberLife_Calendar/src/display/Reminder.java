package display;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import component.Recurrence;
import component.TimePickerList;
import component.reminder.DayOfWeekSelector;
import component.reminder.EndRecurrenceComponent;
import component.reminder.FrequencyComponent;
import component.reminder.IntervalComponent;
import db.functions.CreateReminder;
import db.pojo.ReminderDB;
import db.pojo.ReminderSchedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import statics.SESSION;

public class Reminder extends Scene {

	private Label lblDate;
	private TextField txtName;
	private DatePicker dtDate;
	private CheckBox cbxAllDay, cbxRepeat;
	private Button btnEnviar;
	private Recurrence recurrence;
	private TimePickerList time_picker_list;
	private IntervalComponent interval;
	private RadioButton radTime, radInterval;
	private ToggleGroup radGroup;

	private CreateReminder create_reminder;

	private Label lblRecurrence, lblRepeat;
	private FrequencyComponent frequency;
	private DayOfWeekSelector dayOfWeekSelector;
	private EndRecurrenceComponent endRecurrence;
	
	private VBox vb_recurrence;
	
	public Reminder() {
		super(new HBox());

		this.create_reminder = new CreateReminder();

		recurrence = new Recurrence();
		
		vb_recurrence = new VBox();
		vb_recurrence.setDisable(true);

		time_picker_list = new TimePickerList();


		lblRecurrence = new Label("Recorrência:");
		lblRepeat = new Label("Repetir a cada");
		frequency = new FrequencyComponent();
		dayOfWeekSelector = new DayOfWeekSelector();
		endRecurrence = new EndRecurrenceComponent();
		
		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));
		
//		vb_recurrence.getChildren().addAll(lblRecurrence,lblRepeat,frequency,dayOfWeekSelector);
//		vb_recurrence.getChildren().addAll(endRecurrence);
//		vb_recurrence.setSpacing(15);	
		
		vb.getChildren().addAll(lembrete(vb_recurrence), recurrence);
		
		/* scene */ this.getStylesheets().add(this.getClass().getResource("/css/reminder.css").toExternalForm());
		this.setRoot(vb);

	}

	private VBox lembrete(VBox recorrencia) {

		VBox vb = new VBox();
		vb.setSpacing(20);

		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");

		txtName = new TextField();
		txtName.setPromptText("Título do lembrete");
		txtName.setId("lNome");
		btnEnviar = new Button("Salvar");
		btnEnviar.setId("btnEnviar");

		btnEnviar.setOnAction(evento -> {
//			System.out.println(frequency.get_selected_option());
			System.out.println(endRecurrence.get_amount_repetition());
			System.out.println(recurrence.endRecurrence.get_amount_repetition());
		});
		barraTitulo.getChildren().addAll(txtName, btnEnviar);

		HBox hbData = new HBox();
		hbData.setId("hbData");

		lblDate = new Label("Data:");

		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); // instanciando classe que formata a data em
																		// string
		Date currentDate = new Date(Calendar.getInstance().getTime().getTime()); // criando uma nova data
		LocalDate localDate = LocalDate.parse(dateFormater.format(currentDate)); // criando uma data sem time-zone

		dtDate = new DatePicker(localDate);

		hbData.getChildren().addAll(lblDate, dtDate);

		radGroup = new ToggleGroup();

		HBox hTime = new HBox();
		radTime = new RadioButton();
		radTime.setToggleGroup(radGroup);
		radTime.setSelected(true);
		time_picker_list = new TimePickerList();
		hTime.getChildren().addAll(radTime, time_picker_list);

		HBox hInterval = new HBox();
		radInterval = new RadioButton();
		radInterval.setToggleGroup(radGroup);
		interval = new IntervalComponent();
		hInterval.getChildren().addAll(radInterval, interval);

		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRepetir");

		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

//				timePickerList.setDisable(newValue);
			}
		});

		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				recorrencia.setDisable(!newValue);
			}
		});

		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);

		vb.getChildren().addAll(barraTitulo, hbData, hbRepetir, hTime, hInterval);

		return vb;
	}
	/** 
	 * função para criar lembrete e adicionar seus respectivos horarios 
	 * colocar condição ali para checar qual dos tipos de horario o usuario vai querer usar
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void create_reminder() throws ClassNotFoundException, SQLException {

		ReminderDB reminder = new ReminderDB();

		reminder.setReminder(txtName.getText());
		reminder.setAll_day(cbxAllDay.selectedProperty().get());
		reminder.setRecurrence_by_minute(cbxAllDay.selectedProperty().get());
		reminder.setRepeat(cbxRepeat.selectedProperty().get());
		reminder.setType_recurrence(frequency.get_selected_option());
		reminder.set_user_cod((int) SESSION.get_user_cod());

		this.create_reminder.insert_reminder(reminder);

		insert_shedule();

		/**
		 * time picker selecionado ou repetição hora a hora
		 */
		boolean time_picker_selecionado = radTime.isSelected();
		if (time_picker_selecionado) {

		}
		String value = dtDate.getValue() + " " + time_picker_list.get_selected_time().get(0);
		create_reminder.insert_reminder_schedule(true, value, value, 60, create_reminder.get_reminder_cod());
	}

	private void insert_shedule() {

		boolean all_day_and_repeat = this.cbxAllDay.selectedProperty().get() && this.cbxRepeat.selectedProperty().get();

		if (all_day_and_repeat) {
				
			ReminderSchedule rs = new ReminderSchedule();
			
			
			
			return;
		}
		
		return;
	}

	public void create_reminder_with_time_picker(ReminderDB reminder, CreateReminder c)
			throws ClassNotFoundException, SQLException {

		if (time_picker_list.get_selected_time().isEmpty())
			return; /* nenhum horario selecionado */
		/*
		 * loop para salvar os horarios do lembrete
		 */
		for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {

			/* valor da data e da hora */
			String val = time_picker_list.get_selected_time().get(i);
			String date = dtDate.getValue().toString();

			String date_time = date + " " + val;

			c.insert_reminder_schedule(false, date_time, date_time, 0, c.get_reminder_cod());
		}
		System.out.println("[CONFIRMATION] - stored in the db");
		return;
	}

}

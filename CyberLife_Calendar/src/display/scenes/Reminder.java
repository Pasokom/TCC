package display.scenes;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;

import component.Recurrence;
import component.TimePicker;
import db.functions.appointment.EditAppointment;
import db.functions.reminder.CreateReminder;
import db.pojo.reminderPOJO.ReminderDB;
import db.pojo.reminderPOJO.ReminderEndSchedule;
import db.pojo.reminderPOJO.ReminderSchedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import statics.SESSION;

public class Reminder extends Scene {

	private TextField txt_title;

	private Label img_date;
	private DatePicker dt_start;

	private DatePicker dt_end;

	private Label img_schedule;
	private Label lbl_allday;
	private CheckBox cbx_allday;
	private Label lbl_interval;
	private CheckBox cbx_interval;
	private TimePicker t_start;
	private TimePicker t_end;

	private Label lbl_each;
	private TextField txt_minutes;
	private Label lbl_minutes;

	private AnchorPane container;

	private HBox hb_qtd_interval;

	private StackPane pane;
	private VBox main;
	private HBox tabSelector;
	private Recurrence repetition;

	private ReminderDB reminder;
	private boolean edit = false;

	public Reminder() {
		super(new VBox());

		this.getStylesheets().add(this.getClass().getResource("../../css/reminder.css").toExternalForm());

		main = vbox_mainContent();
		repetition = new Recurrence();

		container = new AnchorPane();

		pane = new StackPane();
		pane.getChildren().add(main);

		tabSelector = hbox_tabSelector();

		AnchorPane.setTopAnchor(pane, 5d);
		AnchorPane.setRightAnchor(pane, 0d);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(tabSelector, 0d);

		container.getChildren().addAll(pane, tabSelector);

		this.setRoot(container);
	}

	public Reminder(ReminderDB reminder) {
		super(new VBox());

		this.reminder = reminder;
		this.edit = true;

		this.getStylesheets().add(this.getClass().getResource("../../css/reminder.css").toExternalForm());

		main = vbox_mainContent();
		repetition = new Recurrence();

		container = new AnchorPane();

		pane = new StackPane();
		pane.getChildren().add(main);

		tabSelector = hbox_tabSelector();

		AnchorPane.setTopAnchor(pane, 5d);
		AnchorPane.setRightAnchor(pane, 0d);
		AnchorPane.setLeftAnchor(pane, 0d);
		AnchorPane.setBottomAnchor(tabSelector, 0d);

		container.getChildren().addAll(pane, tabSelector);

		this.setRoot(container);

		txt_title.setText(reminder.getTitulo());
		cbx_allday.setSelected(reminder.isDia_todo());
		dt_start.setValue(reminder.getHorario().toLocalDateTime().toLocalDate());
		t_start.setValue(reminder.getHorario().toLocalDateTime().toLocalTime());

		if(reminder.getIntervalo_minutos() != 0) {

			cbx_interval.setSelected(true);
			dt_end.setValue(reminder.getHorario_fim().toLocalDateTime().toLocalDate());
			t_end.setValue(reminder.getHorario_fim().toLocalDateTime().toLocalTime());
			txt_minutes.setText(String.valueOf(reminder.getIntervalo_minutos()));
		}
		else {

			dt_end.setValue(reminder.getHorario().toLocalDateTime().toLocalDate());
			t_end.setValue(reminder.getHorario().toLocalDateTime().toLocalTime());
		}

		repetition.setTypeRecurrence(reminder.getTipo_repeticao());
		repetition.setTypeEndRecurrence(reminder.getTipo_fim_repeticao());
		repetition.setInterval(reminder.getSchedule().getIntervalo());
		repetition.setWeek(reminder.getSchedule().getDias_semana());
		repetition.setEndDay(reminder.getReminderEndSchedule().getDia_fim());
		repetition.setQtdRecurrences(reminder.getReminderEndSchedule().getQtd_recorrencia());
	}

	private VBox vbox_mainContent() {

		VBox content = new VBox();
		content.setSpacing(5);

		/* TÃ­tulo */
		txt_title = new TextField();
		txt_title.setId("title");
		txt_title.setPromptText("Titulo");

		/* horario */
		img_schedule = new Label();
		img_schedule.setId("img_schedule");
		lbl_allday = new Label("Dia inteiro");
		cbx_allday = new CheckBox();

		cbx_allday.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				t_start.setDisable(newValue);
				lbl_interval.setDisable(newValue);
				cbx_interval.setDisable(newValue);

				if (cbx_interval.isSelected()) {

					t_end.setDisable(newValue);
					dt_end.setDisable(newValue);
					hb_qtd_interval.setDisable(newValue);
				}
			}
		});

		GridPane pnl_layout = new GridPane();
		pnl_layout.setHgap(5);
		pnl_layout.setVgap(5);

		GridPane.setHalignment(cbx_allday, HPos.RIGHT);

		pnl_layout.add(img_schedule, 0, 0);
		pnl_layout.add(lbl_allday, 1, 0);
		pnl_layout.add(cbx_allday, 2, 0);

		img_date = new Label();
		img_date.setId("img_date");
		dt_start = new DatePicker(LocalDate.now());

		Calendar time = Calendar.getInstance();

		t_start = new TimePicker(time);
		t_start.setPrefWidth(80);

		pnl_layout.add(dt_start, 1, 1);
		pnl_layout.add(t_start, 2, 1);

		/* intervalo */
		lbl_interval = new Label("Intervalo");
		cbx_interval = new CheckBox();
		GridPane.setHalignment(cbx_interval, HPos.RIGHT);

		cbx_interval.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				t_end.setDisable(!newValue);
				dt_end.setDisable(!newValue);
				hb_qtd_interval.setDisable(!newValue);
			}
		});

		pnl_layout.add(lbl_interval, 1, 2);
		pnl_layout.add(cbx_interval, 2, 2);

		time.add(Calendar.HOUR, 1);
		dt_end = new DatePicker(LocalDate.of(time.get(Calendar.YEAR), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH)));
		t_end = new TimePicker(time);
		t_end.setPrefWidth(80);

		t_end.setDisable(true);
		dt_end.setDisable(true);

		pnl_layout.add(dt_end, 1, 3);
		pnl_layout.add(t_end, 2, 3);

		lbl_each = new Label("a cada");
		txt_minutes = new TextField("1");
		txt_minutes.setPrefWidth(30);
		lbl_minutes = new Label("minuto(s)");

		hb_qtd_interval = new HBox();
		hb_qtd_interval.setSpacing(5);
		hb_qtd_interval.setAlignment(Pos.CENTER_LEFT);
		hb_qtd_interval.getChildren().addAll(lbl_each, txt_minutes, lbl_minutes);
		hb_qtd_interval.setDisable(true);

		pnl_layout.add(hb_qtd_interval, 1, 4, 2, 1);

		content.getChildren().addAll(txt_title, pnl_layout);

		return content;
	}

	private HBox hbox_tabSelector() {

		HBox content = new HBox();

		content.getStylesheets().add(this.getClass().getResource("../../css/event_tab_layout.css").toExternalForm());

		content.setId("tab_layout");

		ToggleGroup opt_group = new ToggleGroup();

		ToggleButton opt_1 = new ToggleButton();
		opt_1.setToggleGroup(opt_group);
		opt_1.setId("opt_1");
		opt_1.setSelected(true);
		ToggleButton opt_2 = new ToggleButton();
		opt_2.setToggleGroup(opt_group);
		opt_2.setId("opt_2");

		opt_group.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
			if (newVal == null)
				oldVal.setSelected(true);
		});

		Button btn_done = new Button();
		btn_done.setId("btn_done");

		opt_1.setOnMouseClicked(e -> {

			pane.getChildren().clear();
			pane.getChildren().add(main);
		});

		opt_2.setOnMouseClicked(e -> {

			pane.getChildren().clear();
			pane.getChildren().add(repetition);
		});

		btn_done.setOnAction(e -> {

			if(!this.edit){
				createReminder();
				((Stage)this.getWindow()).close();
			}
			else {

				editReminder();
				((Stage)this.getWindow()).close();
			}
		});

		Region region = new Region();
		HBox.setHgrow(region, Priority.ALWAYS);

		content.prefWidthProperty().bind(container.widthProperty());
		content.getChildren().addAll(opt_1, opt_2, region, btn_done);

		return content;
	}

	private void createReminder() {

		CreateReminder create = new CreateReminder();

		try {

			create.insert_reminder(getReminder());
			HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
			HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		
	}

	private void editReminder() {

		EditAppointment edit = new EditAppointment();
		ReminderDB editReminder = getReminder();
		editReminder.setCod_recorrencia(reminder.getCod_recorrencia());
		edit.edit(editReminder);
		HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
		HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
	}

	private ReminderDB getReminder(){

		String titulo = this.txt_title.getText();
		Timestamp horario = new Timestamp(getDate(this.dt_start, this.t_start));
		Timestamp horario_fim = new Timestamp(getDate(this.dt_end, this.t_end));
		int intervalo_minutos = this.cbx_interval.isSelected() ? Integer.parseInt(this.txt_minutes.getText()) : 0;
		boolean dia_todo = this.cbx_allday.isSelected();
		int tipo_repeticao = this.repetition.getTypeRecurrence();
		int tipo_fim_repeticao = this.repetition.getTypeEndRecurrence();
		int fk_usuario = (int) SESSION.get_user_cod();

		int intervalo = this.repetition.getInterval();
		boolean[] dias_semana = this.repetition.getWeek();

		Date dia_fim = this.repetition.getEndDay();
		int qtd_recorrencia = this.repetition.getQtdRecurrences();

		ReminderDB reminder = new ReminderDB();
		reminder.setTitulo(titulo);
		reminder.setHorario(horario);
		reminder.setHorario_fim(horario_fim);
		reminder.setIntervalo_minutos(intervalo_minutos);
		reminder.setDia_todo(dia_todo);
		reminder.setTipo_repeticao(tipo_repeticao);
		reminder.setTipo_fim_repeticao(tipo_fim_repeticao);
		reminder.setFk_usuario(fk_usuario);

		ReminderSchedule reminderSchedule = new ReminderSchedule();
		reminderSchedule.setIntervalo(intervalo);
		reminderSchedule.setDias_semana(dias_semana);

		ReminderEndSchedule reminderEndSchedule = new ReminderEndSchedule();
		reminderEndSchedule.setDia_fim(dia_fim);
		reminderEndSchedule.setQtd_recorrencia(qtd_recorrencia);

		reminder.setSchedule(reminderSchedule);
		reminder.setReminderEndSchedule(reminderEndSchedule);

		return reminder;
	}

	private long getDate(DatePicker date, TimePicker time){

		Calendar c = Calendar.getInstance();

		c.set(Calendar.DAY_OF_MONTH, date.getValue().getDayOfMonth());
		c.set(Calendar.MONTH, date.getValue().getMonthValue() - 1);
		c.set(Calendar.YEAR, date.getValue().getYear());

		if(!this.cbx_allday.isSelected()){

			c.set(Calendar.HOUR_OF_DAY, time.getHours());
			c.set(Calendar.MINUTE, time.getMinutes());
		}
		else {

			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
		}

		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTimeInMillis();
	}
}
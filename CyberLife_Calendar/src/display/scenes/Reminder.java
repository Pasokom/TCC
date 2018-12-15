package display.scenes;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import component.CustomScroll;
import component.Recurrence;
import component.TimePickerList;
import component.reminder.IntervalComponent;
import db.functions.reminderFUNCTIONS.CreateReminder;
import db.pojo.reminderPOJO.ReminderDB;
import db.pojo.reminderPOJO.ReminderSchedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
import javafx.stage.Stage;
import listeners.windows.CloseWindowEsc;
import statics.Enums;
import statics.SESSION;

public class Reminder extends Scene {

	private Label lblDate;
	private TextField txtName;
	private DatePicker dtDate;
	private CheckBox cbxAllDay, cbxRepeat;
	private Button btnEnviar;
	private TimePickerList time_picker_list;
	private Recurrence recurrence;

	private IntervalComponent interval;
	private RadioButton radTime, radInterval;
	private ToggleGroup radGroup;

	// private VBox vb_recurrence;
	private CreateReminder create_reminder;
	private Stage owner;

	/**
	 * Used to show a screen for creation of a new reminder
	 * 
	 * @param owner
	 */
	public Reminder(Stage owner) {
		super(new HBox());
		this.owner = owner;
		init();
	}

	/**
	 * Used to load the screen with informations about a reminder that already
	 * exists and will be changed
	 * 
	 * @param reminder
	 * @param owner
	 */
	public Reminder(ReminderDB reminder, Stage owner) {
		super(new HBox());
		this.owner = owner;
		init();

		if (reminder.getRecurrenceType() != 0)
			this.cbxRepeat.setSelected(true);


		this.recurrence.setTypeFrequency(reminder.getRecurrenceType());
	
		if (reminder.getRecurrenceType() == 2) 
			setWeekDays(reminder);
		
		
		
	}
	
	/** 
	 * 	
	 * Set the checkboxes of the week days according with the days of the schedule of these reminder
	 * @param ReminderDB
	 */
	public void setWeekDays(ReminderDB r) {
		int[] days = new int[7];

		ArrayList<ReminderSchedule> rs = r.getlReminderSchedule();
		int i = 0;
		while (i < rs.size()) {
			days[i] = rs.get(i).getWeekDay();
			i++;
		}
		i = 0;
		for (; i < days.length; i++)
			this.recurrence.setDay(days[i]);

	}

	public void init() {
		this.setOnKeyPressed(new CloseWindowEsc(owner));
		CustomScroll customScroll = new CustomScroll();

		VBox vb = new VBox();

		customScroll.setComponent(vb);

		recurrence = new Recurrence();
		setVisiblility(recurrence, false);

		time_picker_list = new TimePickerList();
		try {
			this.create_reminder = new CreateReminder();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));
		vb.getChildren().addAll(lembrete(recurrence), recurrence);

		/* scene */ this.getStylesheets().add("css/reminder.css");

		this.setRoot(customScroll);

	}

	private VBox lembrete(VBox recorrencia) {

		VBox vb = new VBox();
		vb.setSpacing(20);

		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");

		txtName = new TextField();
		txtName.setPromptText("T�tulo do lembrete");
		txtName.setId("lNome");
		btnEnviar = new Button("Salvar");
		btnEnviar.setId("btnEnviar");

		btnEnviar.setOnAction(evento -> {
			try {
				insert_reminder_and_schedule();
				this.owner.close();
				HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
				HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
				return;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
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

		setVisiblility(hTime, false);
		setVisiblility(hInterval, false);

		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.setSelected(true);
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				setVisiblility(hTime, !newValue);
				setVisiblility(hInterval, !newValue);
			}
		});

		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				setVisiblility(recorrencia, newValue);
			}
		});

		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);

		vb.getChildren().addAll(barraTitulo, hbData, hbRepetir, hTime, hInterval);
		return vb;
	}

	/**
	 * FUNÇÕES PARA INSERIR LEMBRETE E HORARIOS DE LEMBRETE NO BANCO
	 * 
	 */

	private void insert_reminder_and_schedule() throws SQLException, ClassNotFoundException {

		boolean is_not_repeat = cbxRepeat.selectedProperty().get() == false;
		boolean is_all_day_selected = cbxAllDay.selectedProperty().get();
		boolean is_time_picker = radTime.selectedProperty().get();
		boolean is_never_end_selected = recurrence.is_never_selected();
		boolean is_by_choosed_date = this.recurrence.on_date();
		boolean is_by_amount = this.recurrence.is_by_amount();

		insert_reminder();

		if (is_not_repeat) {
			insert_shedule_no_recurrence(is_all_day_selected, is_time_picker);
			return;
		}
		if (is_never_end_selected) {
			never_end_schedule();
			return;
		}
		// if (time_picker_list.get_selected_time().isEmpty()) {
		// System.out.println("[INFO] time picker vazio, saindo da fun��o");
		if (is_by_choosed_date) {
			schedule_amount_or_date(false);
			return;
		}
		if (is_by_amount) {
			schedule_amount_or_date(true);
			return;
		}
		// }
	}

	private void insert_reminder() {
		ReminderDB reminder = new ReminderDB();

		reminder.setTitle(txtName.getText());
		reminder.setRecurrenceType(
				!this.cbxRepeat.selectedProperty().get() ? 0 : this.recurrence.get_recurrence_type());

		if (cbxAllDay.selectedProperty().get()) {
			reminder.setRepetitionType(Enums.RepetitionType.ALL_DAY.getValue());
			reminder.setActive(true);
			reminder.setUserID((int) SESSION.get_user_cod());
			try {
				create_reminder.insert_reminder(reminder);
				return;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		if (radInterval.selectedProperty().get()) {
			reminder.setRepetitionType(Enums.RepetitionType.INTERVAL.getValue());
			reminder.setActive(true);
			reminder.setUserID((int) SESSION.get_user_cod());
			try {
				create_reminder.insert_reminder(reminder);
				return;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		if (radTime.selectedProperty().get()) {
			reminder.setRepetitionType(Enums.RepetitionType.TIME_PICKER.getValue());
			reminder.setActive(true);
			reminder.setUserID((int) SESSION.get_user_cod());
			try {
				create_reminder.insert_reminder(reminder);
				return;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <h2>testado e funcionando</h2>
	 * 
	 * insere na tabela de horarios de lembrete, insert para lembretes sem
	 * recorrencia pode usar o time picker ou intervalo por horas utiliza a classe
	 * {@link db.functions.CreateReminder}
	 * 
	 * @param is_all_day_selected
	 * @param is_time_picker
	 * @param date
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void insert_shedule_no_recurrence(boolean is_all_day_selected, boolean is_time_picker)
			throws ClassNotFoundException, SQLException {
		/*
		 * se a opção dia todo estiver selecionada entra nessa condição inserts para
		 * essa opção são diferentes pq lá dentro da função é feita uma formatação na
		 * data
		 */
		String date = this.dtDate.getValue().toString();
		if (is_all_day_selected) {
			create_reminder.schedule_without_recurrence(date, new String(), new String(), true, 0);
			return;
		}
		if (is_time_picker) { /* se entrou aqui, então o time picker foi selecionado */
			if (time_picker_list.get_selected_time().isEmpty()) { /* se o time picker estiver vazio ele sai da função */
				System.out.println("[INFO] time picker vazio, saindo da fun��o");
				return;
			}
			/*
			 * loop para formatar os valores do time picker e inserir um a um no banco de
			 * dados
			 */
			for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {
				String val = time_picker_list.get_selected_time().get(i);

				String date_time = date + " " + val;
				create_reminder.schedule_without_recurrence(date_time, new String(), new String(), false, 0);
				System.out.println("\n [INFO] valor " + date_time + "inserido no banco \n \n loop numero : " + i);
			}
			/* quando acabar o loop sai da função */
			return;
		}
		/*
		 * se chegou aqui é pq escolheu intervalos
		 *
		 * montando os horarios de inicio e fim do intervalo
		 */
		int interval = this.interval.selected_interval();
		String time_begin = this.interval.start_time();
		String time_end = this.interval.end_time();
		String date_time = date + " " + time_begin;
		create_reminder.schedule_without_recurrence(date_time, time_begin, time_end, false, interval);

		System.out.println("[INFO] horario de lembrete com intervalo");
		System.out.println("[CONFIRMATION] repetir entre " + time_begin + " e " + time_end + " \n à cada " + interval
				+ " minutos.");
		return;
	}

	/**
	 * <p>
	 * <h2>Insere os horarios de lembretes com recorrencia quando a recorrencia não
	 * tem final</h2>
	 * </p>
	 * <p>
	 * Deve ser ser usada somete se a opção "nunca repete" estiver selecionada
	 * </p>
	 * <h2>Usa a classe {@link CreateReminder} para realização de inserts</h2>
	 * 
	 * <p>
	 * Se o o parametro all_day for true a função do insert fará uma formatação no
	 * horario do lembrete Condicionais para checar qual o tipo de horario o usuario
	 * escolheu, se é com o time picker ou por intervalo de horas.
	 * </p>
	 * 
	 * @author jefter66
	 * @param date
	 * @param all_day
	 * @throws ClassNotFoun dException
	 * @throws SQLException
	 */
	private void never_end_schedule() throws SQLException, ClassNotFoundException {

		boolean by_time_picker = !this.cbxAllDay.selectedProperty().get() ? this.radTime.selectedProperty().get()
				: false;

		boolean by_week = this.recurrence.get_recurrence_type() == Enums.TypeRecurrence.WEEKLY.getValue();
		boolean is_all_day_selected = this.cbxAllDay.selectedProperty().get();

		String time_begin = this.interval.start_time();
		String time_end = this.interval.end_time();
		int interval = this.interval.selected_interval();

		String date_time = dtDate.getValue().toString();

		int recurrence = this.recurrence.get_recurrence_value();

		if (by_week) {

			while (!is_all_day_selected) {
				if (by_time_picker) {
					if (week_day_selected().isEmpty() || time_picker_values().isEmpty())
						return;

					for (int i = 0; i < week_day_selected().size(); i++) {
						int week_day = week_day_selected().get(i);
						int j;
						for (j = 0; j < this.time_picker_values().size(); j++) {

							String date_choosed = this.time_picker_values().get(j);

							this.create_reminder.schedule_recurrence_never_end(date_choosed, new String(), new String(),
									false, 0, recurrence, week_day);
						}
					}
					return;
				}
				for (int i = 0; i < week_day_selected().size(); i++) {
					int week_day = week_day_selected().get(i);
					this.create_reminder.schedule_recurrence_never_end(date_time, time_begin, time_end, false, interval,
							recurrence, week_day);
				}
				return;
			}
			for (int i = 0; i < week_day_selected().size(); i++) {
				int week_day = week_day_selected().get(i);
				this.create_reminder.schedule_recurrence_never_end(date_time, new String(), new String(), true, 0,
						recurrence, week_day);
			}
			return;
		}

		if (by_time_picker) {
			for (int i = 0; i < this.time_picker_values().size(); i++) {
				String value = this.time_picker_values().get(i);
				this.create_reminder.schedule_recurrence_never_end(value, new String(), new String(), false, 0,
						recurrence, 8);
			}
			return;
		}
		this.create_reminder.schedule_recurrence_never_end(date_time, time_begin, time_end, is_all_day_selected,
				interval, recurrence, 7);
		return;
	}

	private void schedule_amount_or_date(boolean is_by_amount) throws SQLException, ClassNotFoundException {

		boolean by_time_picker = !this.cbxAllDay.selectedProperty().get() ? this.radTime.selectedProperty().get()
				: false;
		boolean by_week = this.recurrence.get_recurrence_type() == Enums.TypeRecurrence.WEEKLY.getValue();
		boolean is_all_day_selected = this.cbxAllDay.selectedProperty().get();

		String time_begin = this.interval.start_time();
		String time_end = this.interval.end_time();
		int interval = this.interval.selected_interval();

		String date_begin = dtDate.getValue().toString();
		String date_end = this.recurrence.get_end_date();

		int amount = this.recurrence.get_amount_choosed();
		int recurrence = this.recurrence.get_recurrence_value();
		while (by_week) {

			while (!is_all_day_selected) {
				if (by_time_picker) {
					if (week_day_selected().isEmpty() || time_picker_values().isEmpty())
						return;

					for (int i = 0; i < week_day_selected().size(); i++) {
						int week_day = week_day_selected().get(i);
						int j;
						for (j = 0; j < this.time_picker_values().size(); j++) {

							String date_choosed = this.time_picker_values().get(j);
							if (!is_by_amount)
								this.create_reminder.schedule_recurrence_by_choosed_date(date_choosed, date_end,
										new String(), new String(), false, 0, recurrence, week_day);
							else
								this.create_reminder.schedule_by_amount(date_begin, new String(), new String(), false,
										0, recurrence, week_day, amount);
							// j = 0;
						}
					}
					return;
				}
				for (int i = 0; i < week_day_selected().size(); i++) {
					int week_day = week_day_selected().get(i);
					if (!is_by_amount)
						this.create_reminder.schedule_recurrence_by_choosed_date(date_begin, date_end, time_begin,
								time_end, false, interval, recurrence, week_day);
					else
						this.create_reminder.schedule_by_amount(date_begin, time_begin, time_end, false, interval,
								recurrence, week_day, amount);
				}
				return;
			}
			for (int i = 0; i < week_day_selected().size(); i++) {
				int week_day = week_day_selected().get(i);
				if (!is_by_amount)
					this.create_reminder.schedule_recurrence_by_choosed_date(date_begin, date_end, new String(),
							new String(), true, 0, recurrence, week_day);
				else
					this.create_reminder.schedule_by_amount(date_begin, new String(), new String(), true, 0, recurrence,
							week_day, amount);
			}
		}
		if (by_time_picker) {
			for (int i = 0; i < this.time_picker_values().size(); i++) {
				String value = this.time_picker_values().get(i);
				if (!is_by_amount)
					this.create_reminder.schedule_recurrence_by_choosed_date(value, date_end, time_begin, time_end,
							false, 0, recurrence, 8);
				else
					this.create_reminder.schedule_by_amount(value, new String(), new String(), false, 0, recurrence, 8,
							amount);
			}
			return;
		}
		if (!is_by_amount)
			this.create_reminder.schedule_recurrence_by_choosed_date(date_begin, date_end, time_begin, time_end, false,
					0, recurrence, 7);
		else
			this.create_reminder.schedule_by_amount(date_begin, time_begin, time_end, false, 0, recurrence, 8, amount);

		return;
	}

	/*
	 * como essas esses dois algoritmos estavam se repetindo bastante no codigo
	 * criei funções para eles
	 */
	/**
	 * retorna uma lista com os valores do time picker já formatados
	 * 
	 * @return
	 */
	private ArrayList<String> time_picker_values() {
		ArrayList<String> list = new ArrayList<>();

		String date = dtDate.getValue().toString();

		for (int j = 0; j < time_picker_list.get_selected_time().size(); j++) {
			String val = time_picker_list.get_selected_time().get(j);

			String date_time = date + " " + val;
			list.add(date_time);
		}
		return list;
	}

	/**
	 * @author jefter66
	 *         <p>
	 *         <h2>Retorna os dias da semana selecionados</h2>
	 *         </p>
	 *         Se o dia referente ao valor atual do contador for true, ou seja, foi
	 *         selecionado vai adicionar o valor do contador a uma variavel por
	 *         exemplo : se o dia do indice 0 da lista for true ( ou seja, checkbox
	 *         'domingo' foi selecionada) então o valor da variavel vai ser 0, que
	 *         no banco de dados é domingo
	 * 
	 *         se o dia da semana não for true, a variavel fica valendo 7
	 */
	private ArrayList<Integer> week_day_selected() {
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < this.recurrence.get_selected_day().size(); i++) {
			int day = this.recurrence.get_selected_day().get(i) == true ? i : 7;
			if (day < 7) {
				list.add(day);
			}
		}
		return list;
	}

	private void setVisiblility(Node node, boolean state) {
		node.setVisible(state);
		node.setManaged(state);
	}
}
package display;

<<<<<<< HEAD
=======
import java.io.FileNotFoundException;
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import component.Recurrence;
import component.TimePickerList;
import component.reminder.IntervalComponent;
import db.functions.CreateReminder;
import db.pojo.ReminderBanco;
import db.pojo.ReminderDB;

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
<<<<<<< HEAD
import statics.Enums;
import statics.SESSION;
=======
import main.Main;
import statics.Enums;
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09

public class Reminder extends Scene {

	private Label lblDate;
	private TextField txtName;
	private DatePicker dtDate;
	private CheckBox cbxAllDay, cbxRepeat;
	private Button btnEnviar;
<<<<<<< HEAD
//	private Recurrence recurrence;
	private TimePickerList time_picker_list;
=======
	private Recurrence recurrence;
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
	private IntervalComponent interval;
	private RadioButton radTime, radInterval;
	private ToggleGroup radGroup;

	private TimePickerList time_picker_list;

<<<<<<< HEAD
	private Label lblRecurrence, lblRepeat;
	private FrequencyComponent frequency;
	private DayOfWeekSelector dayOfWeekSelector;
	private EndRecurrenceComponent endRecurrence;

	private VBox vb_recurrence;

	public Reminder() {
		super(new HBox());

		this.create_reminder = new CreateReminder();

//		recurrence = new Recurrence();

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

		vb_recurrence.getChildren().addAll(lblRecurrence, lblRepeat, frequency, dayOfWeekSelector);
		vb_recurrence.getChildren().addAll(endRecurrence);
		vb_recurrence.setSpacing(15);

		vb.getChildren().addAll(lembrete(vb_recurrence), vb_recurrence);
//		vb.getChildren().addAll(lembrete(vb_recurrence), recurrence);

		/* scene */ this.getStylesheets().add(this.getClass().getResource("/css/reminder.css").toExternalForm());
=======
	public Reminder() {
		super(new HBox());

		recurrence = new Recurrence();
		setVisiblility(recurrence, false);

		time_picker_list = new TimePickerList();

		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));
		vb.getChildren().addAll(lembrete(recurrence), recurrence);
		
		/* scene */ this.getStylesheets().add(this.getClass().getResource("../css/reminder.css").toExternalForm());
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
		this.setRoot(vb);

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

<<<<<<< HEAD
			System.out.println(endRecurrence.get_amount_repetition());

=======
			try {
				create_reminder();
				Main.main_stage.setScene(new HomePage());
				
			} catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
				e.printStackTrace();

			} 
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
		});
		barraTitulo.getChildren().addAll(txtName, btnEnviar);
		
		HBox hbData = new HBox();
		hbData.setId("hbData");
		
		lblDate = new Label("Data:");

		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); //instanciando classe que formata a data em string
		Date currentDate = new Date(Calendar.getInstance().getTime().getTime()); //criando uma nova data
 		LocalDate localDate = LocalDate.parse(dateFormater.format(currentDate)); //criando uma data sem time-zone

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

<<<<<<< HEAD
				time_picker_list.setDisable(newValue);
				hInterval.setDisable(newValue);
=======
				setVisiblility(hTime, !newValue);
				setVisiblility(hInterval, !newValue);
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
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
<<<<<<< HEAD

	/**
	 * função para criar lembrete e adicionar seus respectivos horarios colocar
	 * condição ali para checar qual dos tipos de horario o usuario vai querer usar
	 * 
=======
	
	/** 
	 * função para criar lembrete e adicionar seus respectivos horarios 
	 * colocar condição ali para checar qual dos tipos de horario o usuario vai querer usar
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void create_reminder() throws ClassNotFoundException, SQLException {
<<<<<<< HEAD

		ReminderDB reminder = new ReminderDB();

		reminder.setReminder(txtName.getText());
		reminder.setAll_day(cbxAllDay.selectedProperty().get());
		reminder.setRecurrence_by_minute(!cbxAllDay.selectedProperty().get());
		reminder.setRepeat(cbxRepeat.selectedProperty().get());
		reminder.setType_recurrence(frequency.get_selected_option());
		reminder.set_user_cod((int) SESSION.get_user_cod());

		this.create_reminder.insert_reminder(reminder);

		insert_shedule(reminder.getType_recurrence());
=======
		CreateReminder c = new CreateReminder();
		/**
		 * cria o lembrete
		 */
		ReminderBanco reminder = new ReminderBanco();
		reminder.setDia_todo(cbxAllDay.isSelected());
		reminder.setStatus(Enums.ReminderStatus.ENABLED.toString());
		reminder.setTitulo(txtName.getText());
		reminder.setRecorrencia_tipo(Enums.TypeRecurrence.values()[recurrence.get_frequency()].toString());
		c.insert_into_lembrete(reminder);
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09

		/**
		 * time picker selecionado ou repetição hora a hora
		 */
//		boolean time_picker_selecionado = radTime.isSelected();
//		if (time_picker_selecionado) {
<<<<<<< HEAD
//
//		}
//		String value = dtDate.getValue() + " " + time_picker_list.get_selected_time().get(0);
//		create_reminder.insert_reminder_schedule(true, value, value, 60, create_reminder.get_reminder_cod());
	}

	/*
	 * uma só função para inserir os horarios que o usuario escolheu, lembrando que,
	 * o lembrete deve ser inserido antes da execução desse codigo
	 */
	private void insert_shedule(String type_of_recurrence) throws ClassNotFoundException, SQLException {

		boolean is_repetition_selected = this.cbxRepeat.selectedProperty().get();

		if (is_repetition_selected) {

			boolean never_end = endRecurrence.is_never_end_selected();
			boolean end_in = endRecurrence.getChoosed_date().isEmpty();
			boolean amount_repetition = endRecurrence.get_amount_repetition() != 0;

			/*
			 * se a recorrencia é para sempre, os campos de data final não vão ser
			 * preenchidos
			 */
			if (never_end) {
				insert_never_end();
				return;
			}
			/*
			 * condicionais para lembretes com data de fim selecionadas
			 */
			if (end_in) {
				insert_date_or_amount(0);
				return;
			}
			/* salva a quantidade de vezes que irá repetir */
			if (amount_repetition) {
				int amount =  endRecurrence.get_amount_repetition();
				insert_date_or_amount(amount);
				return;
			}
		}
		/*
		 * não tem repetiçao
		 */
		String date = dtDate.getValue().toString();
		int interval = Integer.valueOf(this.interval.selected_interval());
		
		boolean is_all_day = cbxAllDay.selectedProperty().get();
		
		if ( is_all_day ) { 
			create_reminder.all_day_schedule(date, new String(), 0);
			return;
		}
		
		boolean is_interval_selected = radInterval.selectedProperty().get();
		
		if( is_interval_selected) { 
			create_reminder.shedule_repetition(true, date, new String(), 0, interval , 0);
			return;
		}
		
		if( time_picker_list.get_selected_time().isEmpty()) { 
			System.out.println("[INFO] time picker vazio, saindo da funçao");
			return ;
		}
		for ( int i = 0 ; i < time_picker_list.get_selected_time().size(); i++) { 
			String val = time_picker_list.get_selected_time().get(i);

			String date_time = date  + " " + val;
			create_reminder.shedule_repetition(false, date_time, new String(), 0, 0, 0);
			System.out.println("[INFO] valor " + date_time + "inserido no banco \n loop numero : " + i);
		}
	}
	/** 
	 * se for usar a função para inserir em uma data, é importante que o parametro seja zero
	 * @param amount
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void insert_date_or_amount(int amount) throws ClassNotFoundException, SQLException {
		System.out.println("[INFO] função : insert_date_or_amount ");

		String begin_in = dtDate.getValue().toString();
		String end_date = endRecurrence.getChoosed_date();
		int recurrence = frequency.get_choosed_value();
		int interval = Integer.valueOf(this.interval.selected_interval());

		if (cbxAllDay.selectedProperty().get()) {
			create_reminder.all_day_schedule(begin_in, end_date, recurrence);
			return;
		}
		if (radInterval.selectedProperty().get()) {
			System.out.println("[INFO] condicional : escolheu repetição por intervalo de tempo");
			/* se escolheu repetição por intervalos */
			create_reminder.shedule_repetition(true, begin_in, end_date, recurrence, interval, amount);
			return;
		}
		if (radTime.selectedProperty().get()) {
			System.out.println("[INFO] condicional do time picker ");

			if (time_picker_list.get_selected_time().isEmpty())
				return; /* nenhum horario selecionado */
			/*
			 * loop para salvar os horarios do lembrete
			 */
			for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {
				String val = time_picker_list.get_selected_time().get(i);

				String date_time = begin_in + " " + val;

				create_reminder.shedule_repetition(false, date_time, end_date, recurrence, 0, amount);
				System.out.println("[INFO] valor " + date_time + "inserido no banco \n loop numero : " + i);
			}
			System.out.println("[INFO] saindo do função");
			return;
		}
	}

	/*
	 * insere o lembrete sem uma data de final de repetição
	 */
	private void insert_never_end() throws ClassNotFoundException, SQLException {

		System.out.println("[INFO] função: insert_never_end");
		/*
		 * só entra nas condiçoes abaixo se o tipo de recorrencia for semanal
		 */
		if (frequency.get_selected_option().equals(Enums.TypeRecurrence.WEEKLY.get_value())) {
			/*
			 * se a recorrencia for por semana e o usuario escolheu dias para isso, entra
			 * nessa estrutura
			 */
			if (cbxAllDay.selectedProperty().get()) {
				if (!dayOfWeekSelector.selected_day().isEmpty())
					/*
					 * o loop acontece de acordo com o tamanho de uma lista de boolean lá da classe
					 * de selecionar dias
					 */
					/* começando pela segunda = 0 */
					for (int i = 0; i < dayOfWeekSelector.selected_day().size(); i++) {
						/* o valor da recorrencia vai ser o dia da semana ou seja, i */
						/* se não for true, o valor será 7 */
						int week_day = dayOfWeekSelector.selected_day().get(i) == true ? i : 7;

						int recurrence = frequency.get_choosed_value();
						String date = dtDate.getValue().toString();

						if (week_day <= 6) /*
											 * se o valor da recorrencia for menor que o dia da semana, não é inserido
											 */
							create_reminder.all_day_shedule(date, new String(), recurrence, week_day, 0);
					}
				return;
			}
			/* entra na condição de dia todo não selecionado */
			if (!cbxAllDay.selectedProperty().get()) {

				boolean is_interval_selected = radInterval.selectedProperty().get();
				boolean is_time_picker_selected = radTime.selectedProperty().get();

				/*
				 * se o intervalo por minutos estiver selecionado falta converter as horas em
				 * minutos
				 */
				String date = dtDate.getValue().toString();
				int interval = Integer.valueOf(this.interval.selected_interval());
				int recurrence = this.frequency.get_choosed_value();

				if (!dayOfWeekSelector.selected_day().isEmpty()) {

					for (int i = 0; i < dayOfWeekSelector.selected_day().size(); i++) {

						int week_day = dayOfWeekSelector.selected_day().get(i) == true ? i : 7;

						if (week_day <= 6) {
							/*
							 * se a opção de intervalo por minutos estiver selecionado vai
							 */
							if (is_interval_selected) {
								create_reminder.shedule_repetition(true, date, new String(), recurrence, week_day,
										interval, 0);
							}
							if (is_time_picker_selected) {
								/* use else eca */
								if (time_picker_list.get_selected_time().isEmpty())
									return; /* nenhum horario selecionado */
								/*
								 * loop para salvar os horarios do lembrete
								 */
								for (int j = 0; j < time_picker_list.get_selected_time().size(); j++) {
									/* valor da data e da hora */
									String val = time_picker_list.get_selected_time().get(j);

									String date_time = date + " " + val;
									/* insere um varios horarios */
									create_reminder.shedule_repetition(false, date_time, new String(), recurrence,
											week_day, 0, 0);
								}
							}
						}
					}
					return;
				}
			} /* sai da condição "dia todo não selecionado" */
		} /* se o tipo de frequencia não for semanal */

		/**
		 * OUTRAS RECORRENCIAS
		 */

		int recurrence = frequency.get_choosed_value();
		String date = dtDate.getValue().toString();

		boolean is_all_day = cbxAllDay.selectedProperty().get();

		System.out.println("[INFO] opção dia todo selecionada");

		if (is_all_day) {
			create_reminder.all_day_shedule(date, new String(), recurrence, 0, 0);
			System.out.println("[INFO] lembrete inserido");
			return;
		}
		System.out.println("[INFO] a opção dia todo não foi selecionada");
		if (!is_all_day) {
			boolean interval_by_minute = radInterval.selectedProperty().get();

			int interval = Integer.valueOf(this.interval.selected_interval());

			/* se a opção não for intervalo por minutos, tem que ser pelo time picker */
			if (!interval_by_minute) {

				if (time_picker_list.get_selected_time().isEmpty())
					return;

				System.out.println("[INFO] a lista tem alguma coisa");
				System.out.println("[INFO] entrando no loop");
				/*
				 * cada loop pega um valor de dentro da lista de strings do time picker
				 */
				for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {
					String val = time_picker_list.get_selected_time().get(i);

					String date_time = date + " " + val;

					create_reminder.shedule_repetition(false, date_time, new String(), recurrence, 0, 0);

					System.out.println("[INFO] valor " + date_time + "inserido no banco \n loop numero : " + i);
				}
				/*
				 * quando o loop acabar, sai da função
				 */
				return;
			}
			/* se não entrou no loop, insere o intervalo em minutos */
			create_reminder.shedule_repetition(true, date, new String(), recurrence, interval, 0);
		}
		System.out.println("[ERROR] se chegou até aqui, não entrou em nenhuma condição");
=======
//			if (time_picker_list.get_selected_time().isEmpty())
//				return; /* nenhum horario selecionado */
//			for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {
//
//				/* valor da data e da hora */
//				String val = time_picker_list.get_selected_time().get(i);
//				String date = dtDate.getValue().toString();
//				
//				String date_time = date + " " + val;
//
//				c.insert_reminder_schedule(false, date_time, date_time, 0, c.get_reminder_cod());
//			}
//			return;
//		}
//		String value = dtDate.getValue() + " " + time_picker_list.get_selected_time().get(0);
//		c.insert_reminder_schedule(true, value, value, 60, c.get_reminder_cod());
	}
	
	private void setVisiblility(Node node, boolean state) {
		node.setVisible(state);
		node.setManaged(state);
>>>>>>> b1523a8d9520892be0774e0ae5edb118042ccb09
	}
}
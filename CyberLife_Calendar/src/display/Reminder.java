package display;

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
import javafx.stage.Stage;
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

//	private VBox vb_recurrence;
	private CreateReminder create_reminder;

	public Reminder() {
		super(new HBox());

		CustomScroll customScroll = new CustomScroll();
		
		VBox vb = new VBox();
		
		customScroll.setComponent(vb);
		
		recurrence = new Recurrence();
		setVisiblility(recurrence, false);

		time_picker_list = new TimePickerList();
		try {
			this.create_reminder = new CreateReminder();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));
		vb.getChildren().addAll(lembrete(recurrence), recurrence);

		/* scene */ this.getStylesheets().add(this.getClass().getResource("../css/reminder.css").toExternalForm());
		
		this.setRoot(customScroll);

	}

	private void insert_reminder() {

		/**
		 * montando o lembrete
		 */
		ReminderDB reminder = new ReminderDB();

		reminder.setReminder(txtName.getText());

		/*
		 * por enquanto to fazendo isso, depois deixamos o codigo menos feio
		 */
		if (cbxAllDay.selectedProperty().get()) {
			reminder.setAll_day(true);
		}
		if (!cbxAllDay.selectedProperty().get()) {
			reminder.setAll_day(false);
			/*
			 * nem sei se essa recorrencia por minuto vai ser util, mas descobriremos no
			 * futuro
			 */
		}
		reminder.set_user_cod((int) SESSION.get_user_cod());
		reminder.setRepeat(cbxRepeat.selectedProperty().get());
		reminder.setStatus(Enums.ReminderStatus.ENABLED.get_value());
		reminder.setType_recurrence(this.recurrence.get_recurrence_type());
		try {
			create_reminder.insert_reminder(reminder);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
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

			boolean is_not_repeat = cbxRepeat.selectedProperty().get() == false;
			boolean is_all_day_selected = cbxAllDay.selectedProperty().get();
			boolean is_time_picker = radTime.selectedProperty().get();

			boolean is_never_end_selected = recurrence.is_never_selected();

			String date = dtDate.getValue().toString();
			
			insert_reminder();

			if (is_not_repeat) {
				try {
					insert_shedule_no_recurrence(is_all_day_selected, is_time_picker, date);
					((Stage) this.getWindow()).close();
					return;
				} catch (ClassNotFoundException | SQLException e) {
					System.err.println("[ERR0R] problema com o banco de dados");
					e.printStackTrace();
				}
			}
			if (is_never_end_selected) {
				try {
					schedule_repetition_never_end(date, is_all_day_selected, is_time_picker);
					((Stage) this.getWindow()).close();					
					return;
				} catch (ClassNotFoundException | SQLException e) {
//					System.err.println("[ERR0R] problema com o banco de dados");
					e.printStackTrace();
				}
			}
			System.out.println("NAO ENTROU");
			
			
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
	 * função para criar lembrete e adicionar seus respectivos horarios colocar
	 * condição ali para checar qual dos tipos de horario o usuario vai querer usar
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void create_reminder() throws ClassNotFoundException, SQLException {

		ReminderDB reminder = new ReminderDB();

		reminder.setReminder(txtName.getText());
		reminder.setAll_day(cbxAllDay.selectedProperty().get());
//		reminder.setRecurrence_by_minute(!cbxAllDay.selectedProperty().get());
		reminder.setRepeat(cbxRepeat.selectedProperty().get());
		reminder.setType_recurrence(recurrence.get_recurrence_type());
		reminder.set_user_cod((int) SESSION.get_user_cod());

//		this.create_reminder.insert_reminder(reminder);

//		insert_shedule(reminder.getType_recurrence());
		CreateReminder c = new CreateReminder();
		/**
		 * cria o lembrete
		 */
		ReminderBanco reminderD = new ReminderBanco();
		reminderD.setDia_todo(cbxAllDay.isSelected());
		reminderD.setStatus(Enums.ReminderStatus.ENABLED.toString());
		reminderD.setTitulo(txtName.getText());
//		reminderD.setRecorrencia_tipo(Enums.TypeRecurrence.values().recurrence.get_frequency().toString());
		c.insert_into_lembrete(reminderD);
	}

//	uma só função para inserir os horarios que o usuario escolheu, lembrando que,
//	  o lembrete deve ser inserido antes da execução desse codigo private void
	private void insert_shedule(String type_of_recurrence) throws ClassNotFoundException, SQLException {

		boolean is_repetition_selected = this.cbxRepeat.selectedProperty().get();

		if (is_repetition_selected) {

			boolean never_end = recurrence.is_never_selected();
			boolean end_in = recurrence.get_end_date().isEmpty(); // boolean
			boolean amount_repetition = recurrence.get_amount_choosed() != 0;

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

				int amount = recurrence.get_amount_choosed();

				insert_date_or_amount(amount);
				return;
			}
		}

		/*
		 * não tem repetiçao
		 */
		String date = dtDate.getValue().toString();
		int interval_amount = Integer.valueOf(this.interval.selected_interval());

		boolean is_all_day = cbxAllDay.selectedProperty().get();

		if (is_all_day) {
//			create_reminder.all_day_schedule(date, new String(), 0);
			return;
		}

		boolean is_interval_selected = radInterval.selectedProperty().get();

		if (is_interval_selected) {
			create_reminder.shedule_repetition(true, date, new String(), 0, interval_amount, 0);
			return;
		}

		if (time_picker_list.get_selected_time().isEmpty()) {
			System.out.println("[INFO] time picker vazio, saindo da fun��o");
			return;
		}
		for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {
			String val = time_picker_list.get_selected_time().get(i);

			String date_time = date + " " + val;
			create_reminder.shedule_repetition(false, date_time, new String(), 0, 0, 0);
			System.out.println("[INFO] valor " + date_time + "inserido no banco \n loop numero : " + i);
		}
	}

	/**
	 * se for usar a função para inserir em uma data, é importante que o parametro
	 * seja zero
	 * 
	 * @param amount
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void insert_date_or_amount(int amount) throws ClassNotFoundException, SQLException {
		System.out.println("[INFO] fun��o : insert_date_or_amount ");

		String begin_in = dtDate.getValue().toString();
		String end_date = recurrence.get_end_date();
		int recurrence = this.recurrence.get_amount_choosed();
		int interval = Integer.valueOf(this.interval.selected_interval());

		if (cbxAllDay.selectedProperty().get()) {
//			create_reminder.all_day_schedule(begin_in, end_date, recurrence);
			return;
		}
		if (radInterval.selectedProperty().get()) {
			System.out.println("[INFO] condicional : escolheu repeti��o por intervalo de tempo");
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
			System.out.println("[INFO] saindo do fun��o");
			return;
		}
	}

	/*
	 * insere o lembrete sem uma data de final de repetição
	 */
	private void insert_never_end() throws ClassNotFoundException, SQLException {

		System.out.println("[INFO] fun��o: insert_never_end");
		/*
		 * só entra nas condiçoes abaixo se o tipo de recorrencia for semanal
		 */
		if (recurrence.get_recurrence_type() == Enums.TypeRecurrence.WEEKLY.get_value()) {
			/*
			 * se a recorrencia for por semana e o usuario escolheu dias para isso, entra
			 * nessa estrutura
			 */
			if (cbxAllDay.selectedProperty().get()) {
				if (!recurrence.get_selected_day().isEmpty())
					/*
					 * o loop acontece de acordo com o tamanho de uma lista de boolean lá da classe
					 * de selecionar dias
					 */
					/* começando pela segunda = 0 */
					for (int i = 0; i < recurrence.get_selected_day().size(); i++) {
						/* o valor da recorrencia vai ser o dia da semana ou seja, i */
						/* se não for true, o valor será 7 */
						int week_day = recurrence.get_selected_day().get(i) == true ? i : 7;

						int recurrence = this.recurrence.get_amount_choosed();
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
				int recurrence = this.recurrence.get_amount_choosed();

				if (!this.recurrence.get_selected_day().isEmpty()) {

					for (int i = 0; i < this.recurrence.get_selected_day().size(); i++) {

						int week_day = this.recurrence.get_selected_day().get(i) == true ? i : 7;

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
		int recurrence = this.recurrence.get_amount_choosed();
		String date = dtDate.getValue().toString();

		boolean is_all_day = cbxAllDay.selectedProperty().get();

		System.out.println("[INFO] op��o dia todo selecionada");

		if (is_all_day) {
			create_reminder.all_day_shedule(date, new String(), recurrence, 0, 0);
			System.out.println("[INFO] lembrete inserido");
			return;
		}
		if (!is_all_day)
			System.out.println("[INFO] a op��o dia todo n�o foi selecionada");

		int interval = Integer.valueOf(this.interval.selected_interval());
		boolean interval_by_minute = radInterval.selectedProperty().get();
		/*
		 * se a opção não for intervalo por minutos, tem que ser pelo time picker
		 */
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
		System.out.println("[ERROR] se chegou at� aqui, n�o entrou em nenhuma condi��o");
	}

	/**
	 * <H1>testado e funcionando</H1>
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
	private void insert_shedule_no_recurrence(boolean is_all_day_selected, boolean is_time_picker, String date)
			throws ClassNotFoundException, SQLException {
		/*
		 * se a opção dia todo estiver selecionada entra nessa condição inserts para
		 * essa opção são diferentes pq lá dentro da função é feita uma formatação na
		 * data
		 */
		if (is_all_day_selected) {
			create_reminder.schedule_without_recurrence(date, new String(), true, 0);
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

				create_reminder.schedule_without_recurrence(date_time, new String(), false, 0);
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
		String date_begin = date + " " + this.interval.start_time();
		String date_end = date + " " + this.interval.end_time();

		create_reminder.schedule_without_recurrence(date_begin, date_end, false, interval);
		System.out.println("[INFO] horario de lembrete com intervalo");
		System.out.println("[CONFIRMATION] repeti��o entre " + date_begin + " e " + date_end + " \n � cada " + interval
				+ " minutos.");
		return;
	}

	/**
	 * <p>
	 * <h1>Insere os horarios de lembretes com recorrencia quando a recorrencia não
	 * tem final</h1>
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
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void schedule_repetition_never_end(String date, boolean all_day, boolean is_time_picker_selected)
			throws ClassNotFoundException, SQLException {
		/*
		 * tem que saber se o tipo de recorrencia é semanal para poder fazer o loop
		 * dos dias selecionados
		 */
		boolean is_week_recurrence = this.recurrence.get_recurrence_type() == Enums.TypeRecurrence.WEEKLY.get_value();
		int recurrence = this.recurrence.get_recurrence_value();
		
		/* aqui dentro acontece os inserts dos dias da semana */
		if (is_week_recurrence) {
			if (!all_day) {
				/* entra aqui se o time picker estiver selecionado */
				if (is_time_picker_selected) {
					/* se a lista de dias nao estiver vazia */
					if (!week_day_selected().isEmpty()) {

						/* loop vai acontecer de acordo com a quantidade de dias escolhidos */
						for (int i = 0; i < week_day_selected().size(); i++) {

							int week_day = week_day_selected().get(i);

							/* a condição só vai aceitar dias entre 0 e 6, pq assim que funciona no db */
							if (is_time_picker_selected) {
								if (time_picker_list.get_selected_time().isEmpty())
									return; /* nenhum horario selecionado */

								/*
								 * para cada dia selecionado, vai inserir um time picker diferente, os
								 * selecionados, claro
								 */
								int j;
								for (j = 0; j < this.time_picker_values().size(); j++) {

									String date_choosed = this.time_picker_values().get(j);

									create_reminder.schedule_recurrence_never_end(date_choosed, new String(), false, 0,
											recurrence, week_day);
								}
								j = 0;
							}
						}
						System.out.println("[INFO] esse return � util \n [INFO] aproximadamente linha : 631");
						return;
					}
				} /* se o time picker não estiver selecionado */
				int interval = this.interval.selected_interval();
				String date_begin = date + " " + this.interval.start_time();
				String date_end = date + " " + this.interval.end_time();

				for (int i = 0; i < week_day_selected().size(); i++) {
					int week_day = week_day_selected().get(i);
					create_reminder.schedule_recurrence_never_end(date_begin, date_end, false, interval, recurrence,week_day);
					System.out.println("[INFO] inserindo data de repeti��o por intervalo \n intera��o n�:" + i + " do loop");
				}
				System.out.println("[INFO] esse return � �til \n [INFO] aproximadamente linha : 644");
				return;
			}
			/* se a opção 'dia todo' for selecionada */
			for (int i = 0; i < week_day_selected().size(); i++) {
				int week_day = this.week_day_selected().get(i);
				create_reminder.schedule_recurrence_never_end(date, new String(), true, 0, recurrence, week_day);
			}
			return;
		}
		/* recorrencia diferente de semanal */
		if (all_day) { 
			create_reminder.schedule_recurrence_never_end(date, new String(), true, 0, recurrence, 0);
			return;
		}
		
		if (is_time_picker_selected) { 
			for (int i = 0 ; i < this.time_picker_values().size(); i++ ) {
				String right_date = this.time_picker_values().get(i);
				create_reminder.schedule_recurrence_never_end(right_date, new String(), false, 0, recurrence, 0);
			}
			return;
		}
		int interval = this.interval.selected_interval();
		String date_begin = date + " " + this.interval.start_time();
		String date_end = date + " " + this.interval.end_time();
		create_reminder.schedule_recurrence_never_end(date_begin, date_end, all_day, interval, recurrence, 0);
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
	 *         <h1>Retorna os dias da semana selecionados</h1>
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
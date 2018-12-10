package display.scenes;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import component.CustomScroll;
import component.Recurrence;
import component.reminder.TimePicker;
import db.functions.event.CreateEvent;
import db.functions.event.ManageEvents;
import db.pojo.eventPOJO.EventDB;
import db.pojo.eventPOJO.EventEndSchedule;
import db.pojo.eventPOJO.EventSchedule;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import statics.Enums;
import statics.NotifyUser;

public class Event extends Scene {

	private Label lblStartDate, lblEndDate, lblPlace;
	private TextField txtTitle, txtPlace;
	private TextArea txtDetails;
	private Button btnSave;
	private DatePicker dtStart, dtEnd;
	private TimePicker timeStart, timeEnd;
	private CheckBox cbxAllDay, cbxRepeat;
	private Recurrence recurrence;
	private CreateEvent createEvent;

	public Event() {
		super(new VBox());
		init();
	}

	public Event(EventDB event, Stage owner) {
		super(new VBox());
		init();

		EventSchedule es = event.getHorario_evento();
		EventEndSchedule ens = event.getHorario_fim_evento();

		this.txtPlace.setText(event.getLocal_evento());
		this.txtDetails.setText(event.getDescricao());
		this.txtTitle.setText(event.getTitulo());

		this.dtStart = new DatePicker(event.getData_inicio().toLocalDateTime().toLocalDate());
		this.dtEnd = new DatePicker(event.getData_fim().toLocalDateTime().toLocalDate());

		/* para horarios definidos para o projeto */
		String time_begin = event.getEventTime(String.valueOf(event.getData_inicio()));
		String time_end = event.getEventTime(String.valueOf(event.getData_fim()));

		/**
		 * dia todo e repeticao
		 */
		this.cbxAllDay.setSelected(event.isDia_todo());
		if (!event.isDia_todo()) { // se a opção 'dia todo ' estiver selecionada, os horarios não vão ficar
									// visiveis
			this.timeEnd.setText(time_end);
			this.timeStart.setText(time_begin);
		}

		if (event.getTipo_repeticao() > 0) {
			this.cbxRepeat.setSelected(true);
			if (event.getTipo_repeticao() == Enums.TypeRecurrence.WEEKLY.getValue()) {
				// System.out.println(es.getDias_semana().length + " dias");
				setWeekDays(es.getDias_semana());
			}
		}
		/**
		 * frequency component
		 */
		this.recurrence.setTypeFrequency(event.getTipo_repeticao());
		this.recurrence.setChoosedValue(es.getIntervalo());

		/** end recurrence */
		boolean never_end = ens.getQtd_recorrencias() != 0 || ens.getDia_fim() == null;
		boolean by_date = ens.getQtd_recorrencias() == 0 && ens.getDia_fim() != null;
		boolean by_amount = ens.getQtd_recorrencias() > 0;
		
		if (never_end) {
			this.recurrence.setSelectionEnd(0);
		}
		if (by_date) {
			this.recurrence.setSelectionEnd(1);
			this.recurrence.setDatePickerValue(new DatePicker(ens.getDia_fim().toLocalDate()));
		} 
		if(by_amount){
			this.recurrence.setSelectionEnd(2);
			this.recurrence.setSpinnerValue(ens.getQtd_recorrencias());
		}
		this.btnSave.setOnAction(e -> {
			changeEvent(event);
			changeSchedules(es, ens);
			owner.close();
		});
	}
	private void init() {
		Main.main_stage.setTitle("EVENTO");
		/* scene */ this.getStylesheets().add("css/event.css");

		VBox vb = new VBox();

		CustomScroll customScroll = new CustomScroll();

		customScroll.setComponent(vb);

		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));

		/* barra de titulo */
		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");

		txtTitle = new TextField();
		txtTitle.setPromptText("Título do evento");
		txtTitle.setId("lNome");
		btnSave = new Button("Salvar");
		btnSave.setId("btnEnviar");
		btnSave.setOnAction(evento -> {

			try {
				insert_event();
				((Stage) this.getWindow()).close();

				String notificationMessage = "O evento \"" + txtTitle.getText() + "\" foi cadastrado no dia "
						+ dtStart.getValue().toString();

				NotifyUser.sendNotification("Evento", notificationMessage, MessageType.NONE);

				HomePage.listCalendar.update(Calendar.getInstance());

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		});

		barraTitulo.getChildren().addAll(txtTitle, btnSave);
		/* fim barra de titulo */

		/* barra de data e hora */
		HBox dateTimeBar = new HBox();

		java.util.Date data = new java.util.Date();
		LocalDate date = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		lblStartDate = new Label("De");
		dtStart = new DatePicker(date);
		timeStart = new TimePicker(false);
		lblEndDate = new Label("at�");
		dtEnd = new DatePicker(date);
		timeEnd = new TimePicker(false);

		dateTimeBar.getChildren().addAll(lblStartDate, dtStart, timeStart, lblEndDate, dtEnd, timeEnd);
		/* fim da barra de data e hora */

		/* barra repetir e dia inteiro */
		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRepetir");

		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				timeStart.setVisible(!newValue);
				timeEnd.setVisible(!newValue);
				timeStart.setManaged(!newValue);
				timeEnd.setManaged(!newValue);
			}
		});

		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				recurrence.setVisible(newValue);
				recurrence.setManaged(newValue);
			}
		});

		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);
		/* fim barra repetir e dia inteiro */

		/* local */
		HBox hbPlace = new HBox();
		lblPlace = new Label("Local");
		txtPlace = new TextField();
		txtPlace.setPrefWidth(300);

		hbPlace.getChildren().addAll(lblPlace, txtPlace);
		/* fim local */

		txtDetails = new TextArea();
		txtDetails.setPromptText("Adicionar detalhes");
		txtDetails.setMaxWidth(580);

		recurrence = new Recurrence();

		recurrence.setVisible(false);
		recurrence.setManaged(false);

		vb.getChildren().addAll(barraTitulo, dateTimeBar, hbRepetir, hbPlace, txtDetails, recurrence);
		this.setRoot(customScroll);
	}

	/*
	 * insere o evento no banco de dados
	 */
	private void insert_event() throws ClassNotFoundException, SQLException {

		int tipo_repeticao = cbxRepeat.isSelected() ? recurrence.get_recurrence_type() : 0;

		Timestamp data_inicio = Timestamp.valueOf(dtStart.getValue().toString() + " "
				+ (cbxAllDay.isSelected() ? "00:00:00" : timeStart.get_value() + ":00"));
		Timestamp data_fim = Timestamp.valueOf(dtEnd.getValue().toString() + " "
				+ (cbxAllDay.isSelected() ? "00:00:00" : timeEnd.get_value() + ":00"));

		this.createEvent = new CreateEvent();

		EventDB event = new EventDB();
		event.setTitulo(txtTitle.getText());
		event.setData_inicio(data_inicio);
		event.setData_fim(data_fim);
		event.setDia_todo(cbxAllDay.isSelected());
		event.setLocal_evento(txtPlace.getText());
		event.setDescricao(txtDetails.getText());
		event.setTipo_repeticao(tipo_repeticao);
		event.setTipo_fim_repeticao(recurrence.getSelectedEnd());

		int fk = createEvent.insert_event(event);

		EventSchedule schedule = new EventSchedule();
		schedule.setIntervalo(recurrence.get_recurrence_value());
		schedule.setDias_semana(recurrence.get_selected_days());
		schedule.setFk_evento(fk);

		createEvent.insert_event_schedule(schedule);

		EventEndSchedule endSchedule = new EventEndSchedule();
		endSchedule.setDia_fim(Date.valueOf(recurrence.get_end_date()));
		endSchedule.setQtd_recorrencias(recurrence.get_amount_choosed());
		endSchedule.setFk_evento(fk);

		createEvent.insert_event_end_schedule(endSchedule);
	}
	
	private void changeSchedules(EventSchedule es, EventEndSchedule ens) {
		ManageEvents changes = new ManageEvents();

		if (cbxRepeat.selectedProperty().get()) {

			if (!compare(es.getDias_semanaToString(), buildWeekDays(), 0))
				changes.changeRepetition(buildWeekDays(), es.getCod_repeticao(),
						ManageEvents.changeTheRepetition.WEEK_DAYS, "E_REPETIR");
			if (!compare(es.getIntervalo(), this.recurrence.get_recurrence_value(), 1))
				changes.changeRepetition(this.recurrence.get_recurrence_value(), es.getCod_repeticao(),
						ManageEvents.changeTheRepetition.INTERVAL, "E_REPETIR");

			if (this.recurrence.is_never_selected()) {
				changes.changeRepetition(" NULL ", ens.getCod_fim_repeticao(), ManageEvents.changeTheRepetition.END_DAY,
						"E_FIM_REPETICAO");
				changes.changeRepetition(0, ens.getCod_fim_repeticao(),
						ManageEvents.changeTheRepetition.AMOUNT_OF_RECURRENCES, "E_FIM_REPETICAO");
			}
			if (this.recurrence.is_by_amount()) {
				changes.changeRepetition(this.recurrence.get_amount_choosed(), ens.getCod_fim_repeticao(),
						ManageEvents.changeTheRepetition.AMOUNT_OF_RECURRENCES, "E_FIM_REPETICAO");
				changes.changeRepetition(" ", ens.getCod_fim_repeticao(), ManageEvents.changeTheRepetition.END_DAY,
						"E_FIM_REPETICAO");
			}
			if (this.recurrence.on_date()) {
				changes.changeRepetition(this.recurrence.get_end_date(), ens.getCod_fim_repeticao(),
						ManageEvents.changeTheRepetition.END_DAY, "E_FIM_REPETICAO");
				changes.changeRepetition(0, ens.getCod_fim_repeticao(),
						ManageEvents.changeTheRepetition.AMOUNT_OF_RECURRENCES, "E_FIM_REPETICAO");
			}
		}
	}
	private void changeEvent(EventDB event) {
		ManageEvents applyChanges = new ManageEvents();

		if (!compare(event.getTitulo(), this.txtTitle.getText(), 0))
			applyChanges.changeEvent(this.txtTitle.getText(), event.getCod_evento(), ManageEvents.changeTheEvent.TITLE);

		if (!compare(event.getDescricao(), this.txtDetails.getText(), 0)) {
			applyChanges.changeEvent(this.txtDetails.getText(), event.getCod_evento(),ManageEvents.changeTheEvent.DESCRIPTION);
		}
		if (!compare(event.getLocal_evento(), this.txtPlace.getText(), 0)) {
			applyChanges.changeEvent(this.txtPlace.getText(), event.getCod_evento(),ManageEvents.changeTheEvent.EVENT_LOCATION);
		}
		String date_begin = retrieveInRightFormat(this.dtStart.getValue().toString(),this.timeStart.get_value().toString());
		String old_date_begin = retrieveInRightFormat(event.getData_inicio().toString().substring(0, 10),event.getData_inicio().toString().substring(11, 16));
		
		System.out.println( "1 : " + date_begin +  " \n 2 : " + old_date_begin);
		if (!compare(old_date_begin, date_begin, 0)) {
			if (!cbxAllDay.selectedProperty().get()) {
				applyChanges.changeEvent(date_begin, event.getCod_evento(), ManageEvents.changeTheEvent.DATE_BEGIN);
			} else {
				date_begin = retrieveInRightFormat(this.dtStart.getValue().toString(), new String());
				applyChanges.changeEvent(date_begin, event.getCod_evento(), ManageEvents.changeTheEvent.DATE_BEGIN);
			}
		}
		String date_end = retrieveInRightFormat(this.dtEnd.getValue().toString(), this.timeEnd.get_value().toString());
		String old_date_end = old_date_begin = retrieveInRightFormat(event.getData_fim().toString().substring(0, 10),event.getData_fim().toString().substring(11, 16));
		if (!compare(old_date_end, date_end, 0)) {
			if (!cbxAllDay.selectedProperty().get()) {
				applyChanges.changeEvent(date_end, event.getCod_evento(), ManageEvents.changeTheEvent.DATE_END);
			} else {
				date_end = retrieveInRightFormat(this.dtEnd.getValue().toString(), new String());
				applyChanges.changeEvent(date_end, event.getCod_evento(), ManageEvents.changeTheEvent.DATE_END);
			}
		}
		if (!compare(event.isDia_todo(), this.cbxAllDay.selectedProperty().get(), 2)) {
			applyChanges.changeEvent(this.cbxAllDay.selectedProperty().get(), event.getCod_evento(),ManageEvents.changeTheEvent.ALL_DAY);
		}
		if (this.cbxRepeat.selectedProperty().get()) {
			if (!compare(event.getTipo_repeticao(), recurrence.get_recurrence_type(), 1))
				applyChanges.changeEvent(this.recurrence.get_recurrence_type(), event.getCod_evento(),ManageEvents.changeTheEvent.TYPE_OF_REPETITION);
			if (!compare(event.getTipo_fim_repeticao(), recurrence.getSelectedEnd(), 1))
				applyChanges.changeEvent(this.recurrence.getSelectedEnd(), event.getCod_evento(),ManageEvents.changeTheEvent.TYPE_OF_REPETITION_END);
		}
	}
	private String retrieveInRightFormat(String date_value, String time_value) {
		
		String complete_value = date_value + " " + time_value;
		
		if (complete_value.length() == 19)
			return complete_value;
		
		complete_value += ":00";
		if(complete_value.length() == 19) 
			return complete_value;
		
		if(time_value.isEmpty()) 
			return date_value + "00:00:00";
		return  "00:00";
	}
	private String buildWeekDays() {
		StringBuilder stb = new StringBuilder();
		int i = 0;
		boolean[] vector = recurrence.get_selected_days();
		System.out.println(vector.length + " tamanho vetor");
		while (i < vector.length) {
			if (i != 0)
				stb.append(',');
			if (!vector[i]) {
				stb.append('0');
				i++;
				continue;
			}
			stb.append("1");
			i++;
		}
		return stb.toString();
	}

	private boolean compare(Object oldValue, Object newValue, int type_object) {
		if (type_object == 0)
			return String.valueOf(oldValue).equals(String.valueOf(newValue));
		if (type_object == 1)
			return (int) oldValue == (int) newValue;
		if (type_object == 2)
			return (boolean) oldValue == (boolean) newValue;
		return false;
	}

	private void setWeekDays(boolean[] days) {
		int i = 0;
		while (i < days.length) {
			if (days[i] == true)
				this.recurrence.setDay(i);
			i++;
		}
	}

	
	public void displayTray() throws AWTException, MalformedURLException {
		// Obtain only one instance of the SystemTray object
		SystemTray tray = SystemTray.getSystemTray();

		// If the icon is a file
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		// Alternative (if the icon is on the classpath):
		// Image image =
		// Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		// Let the system resize the image if needed
		trayIcon.setImageAutoSize(true);
		// Set tooltip text for the tray icon
		trayIcon.setToolTip("System tray icon demo");
		tray.add(trayIcon);

		// trayIcon.displayMessage("Evento",
		// "O evento \"" + txtTitle.getText() + "\" foi cadastrado no dia " +
		// dtStart.getValue().toString(),
		// MessageType.INFO);
	}
}
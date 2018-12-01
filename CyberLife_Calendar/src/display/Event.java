package display;

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

import component.CustomScroll;
import component.Recurrence;
import component.reminder.TimePicker;
import db.functions.event.CreateEvent;
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

		/* scene */ this.getStylesheets().add(this.getClass().getResource("../css/event.css").toExternalForm());

		VBox vb = new VBox();

		CustomScroll customScroll = new CustomScroll();

		customScroll.setComponent(vb);

		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));

		/* barra de titulo */
		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");

		txtTitle = new TextField();
		txtTitle.setPromptText("T�tulo do evento");
		txtTitle.setId("lNome");
		btnSave = new Button("Salvar");
		btnSave.setId("btnEnviar");
		btnSave.setOnAction(evento -> {

			try {
				insert_event();
				((Stage) this.getWindow()).close();
				
				String notificationMessage = "O evento \"" + txtTitle.getText() 
					+ "\" foi cadastrado no dia " + dtStart.getValue().toString();
				
				NotifyUser.sendNotification("Evento", notificationMessage, MessageType.NONE);

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		});

		barraTitulo.getChildren().addAll(txtTitle, btnSave);
		/* fim barra de titulo */

		/* barra de data e hora */
		HBox dateTimeBar = new HBox();

		lblStartDate = new Label("De");
		dtStart = new DatePicker();
		timeStart = new TimePicker(false);
		lblEndDate = new Label("at�");
		dtEnd = new DatePicker();
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

//		trayIcon.displayMessage("Evento",
//				"O evento \"" + txtTitle.getText() + "\" foi cadastrado no dia " + dtStart.getValue().toString(),
//				MessageType.INFO);
	}
}

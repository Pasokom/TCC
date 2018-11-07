import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import component.Recurrence;
import component.TimePickerList;
import db.functions.CreateReminder;
import db.pojo.ReminderDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Reminder extends Scene {

	private Label lblDate;
	private TextField txtName;
	private DatePicker dtDate;
	private CheckBox cbxAllDay, cbxRepeat;
	private Button btnEnviar;
	private Recurrence recurrence;
	private TimePickerList time_picker_list;

	public Reminder() {
		super(new HBox());

		lblDate = new Label("Data:");

		recurrence = new Recurrence();
		recurrence.setDisable(true);

		time_picker_list = new TimePickerList();

		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setPadding(new Insets(20, 35, 50, 35));
		vb.getChildren().addAll(lembrete(recurrence), time_picker_list, recurrence);

		/* scene */ this.getStylesheets().add(this.getClass().getResource("css/estilo.css").toExternalForm());

		this.setRoot(vb);

		this.getStylesheets().add(this.getClass().getResource("css/estilo.css").toExternalForm());

		this.setRoot(vb);
		vb.requestFocus();
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
				create_reminder();
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

//		timePickerList = new TimePickerList();

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

		vb.getChildren().addAll(barraTitulo, hbData, hbRepetir);

		return vb;
	}
	
	/** 
	 * função para criar lembrete e adicionar seus respectivos horarios 
	 * colocar condição ali para checar qual dos tipos de horario o usuario vai querer usar
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void create_reminder() throws ClassNotFoundException, SQLException {
		CreateReminder c = new CreateReminder();
		/**
		 * cria o lembrete
		 */
		ReminderDB reminder = new ReminderDB();
		reminder.setAll_day(cbxAllDay.isSelected());
		reminder.setRepeat(cbxRepeat.isSelected());
		reminder.setReminder(txtName.getText());
		c.insert_reminder(reminder);

		/**
		 * time picker selecionado ou repetição hora a hora
		 */
		boolean time_picker_selecionado = true;
		if (time_picker_selecionado) {
			if (time_picker_list.get_selected_time().isEmpty())
				return; /* nenhum horario selecionado */
			for (int i = 0; i < time_picker_list.get_selected_time().size(); i++) {

				/* valor da data e da hora */
				String val = time_picker_list.get_selected_time().get(i);
				String date = dtDate.getValue().toString();
				
				String date_time = date + " " + val;

				c.insert_reminder_schedule(false, date_time, date_time, 0, c.get_reminder_cod());
			}
			return;
		}
		String value = dtDate.getValue() + " " + time_picker_list.get_selected_time().get(0);
		c.insert_reminder_schedule(true, value, value, 60, c.get_reminder_cod());
	}
}







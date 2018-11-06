import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import db.Database;
import db.functions.CreateReminder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Reminder extends Scene {

	private Database database;

	private Label lblDate, lblTime, lblRecurrence, lblRepeat, lblEndRepeat, lblOccurrence;

	private TextField txtName;

	private TimePicker txtTime;

	private DatePicker dtDate, dtEndRepeatDate;

	private CheckBox cbxAllDay, cbxRepeat;

	private Spinner<Integer> spnRepeat, spnQtdRepeat;

	private ChoiceBox<String> chbRepeatOptions;

	private CheckBox cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab;

	private ToggleGroup togEndRepeat;
	private RadioButton radNever, radOn, radAfter;

	private Button btnEnviar, btnAddHora;

	public Reminder() { // Database database) {
		super(new HBox());
		this.database = database; // recebendo o banco de dados do construtor
		// this.initStyle(StageStyle.UNDECORATED);

		lblDate = new Label("Data:");
		lblTime = new Label("Hora:");
		lblRecurrence = new Label("Recorrência:");
		lblRepeat = new Label("Repetir a cada");
		lblEndRepeat = new Label("Termina");
		lblOccurrence = new Label("ocorrência");
		VBox recorrencia = recorrencia();
		recorrencia.setDisable(true);

		VBox vb = new VBox();
		vb.getChildren().addAll(lembrete(recorrencia), recorrencia);

//		Scene scene = new Scene(vb);
		/* scene */ this.getStylesheets().add(this.getClass().getResource("css/estilo.css").toExternalForm());

		this.setRoot(vb);
//		this.setScene(scene);
//		this.show();
	}

	private VBox lembrete(VBox recorrencia) {

		VBox vb = new VBox();

		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");

		txtName = new TextField();
		txtName.setPromptText("Nome");
		txtName.setId("lNome");
		btnEnviar = new Button("Salvar");
		btnEnviar.setId("btnEnviar");
		btnEnviar.setOnAction(evento -> {

			// TODO inserir todos os dados do formulario na tabela
			// int allDay = cbxAllDay.isSelected() ? 1 : 0;
			int repeat = cbxRepeat.isSelected() ? 1 : 0;
//			database.queryLembrete(txtName.getText(), "", dtDate.getValue(), repeat); // fazendo um insert no banco de
																						// dados });
			
			CreateReminder c = new CreateReminder();
			
			
			System.out.println(dtDate.getValue());
			
			
//			try {
			
				
				java.sql.Date date = java.sql.Date.valueOf(dtDate.getValue());//yy + "-" + mm + "-" + dd);
						
				Time hour = java.sql.Time.valueOf(txtTime.get_time_selected());
				try {
					c.set_reminder_hour_date(hour,date);
				} catch (ClassNotFoundException | ParseException | SQLException e) {
					System.out.println("[ERROR] parse error");
					
					e.printStackTrace();
				}
				
//			} catch (ClassNotFoundException | ParseException | SQLException e) {
//				e.printStackTrace();
//			}
			
		
		
		});
		barraTitulo.getChildren().addAll(txtName, btnEnviar);

		HBox hbData = new HBox();
		hbData.setId("hbData");

		lblDate = new Label("Data:");

		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); // instanciando classe que formata a data em
																		// string
		Date currentDate = new Date(); // criando uma nova data
		LocalDate localDate = LocalDate.parse(dateFormater.format(currentDate)); // criando uma data sem time-zone

		dtDate = new DatePicker(localDate);
		

		hbData.getChildren().addAll(lblDate, dtDate);

		HBox horarios = new HBox();
		horarios.setId("hbHorarios");

		lblTime = new Label("Horarios:");

		HBox horas = new HBox();
		horas.setId("hbHoras");

		txtTime = new TimePicker();
		btnAddHora = new Button("+");

		btnAddHora.setOnAction(evento -> {

			if (horas.getChildren().size() < 5) {
				horas.getChildren().add(new TimePicker());
			}
		});

		horas.getChildren().add(txtTime);

		horarios.getChildren().addAll(lblTime, horas, btnAddHora);

		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRepetir");

		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				horarios.setDisable(newValue);
			}
		});

		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
			public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {

				recorrencia.setDisable(!newValue);
			}
		});

		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);

		vb.getChildren().addAll(barraTitulo, hbData, hbRepetir, horarios);

		return vb;
	}

	private VBox recorrencia() {

		lblRecurrence = new Label("Recorr�ncia:");
		lblRepeat = new Label("Repetir a cada");
		lblEndRepeat = new Label("Termina");
		lblOccurrence = new Label("ocorr�ncia(s)");
		lblEndRepeat = new Label("Termina");

		chbRepeatOptions = new ChoiceBox<>();
		// populando a caixa de escolha
		chbRepeatOptions.setItems(FXCollections.observableArrayList("dia", "semana", "m�s", "ano"));
		chbRepeatOptions.getSelectionModel().select(1); // definindo o segundo item da lista como o padrao

		// criando dois spinners de 1 a 100 de 1 em 1
		SpinnerValueFactory<Integer> repeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		SpinnerValueFactory<Integer> qtdRepeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
				1);

		spnRepeat = new Spinner<>();
		spnRepeat.setValueFactory(repeatValueFactory);
		spnRepeat.setPrefWidth(80); // alterando a largura

		spnQtdRepeat = new Spinner<>();
		spnQtdRepeat.setValueFactory(qtdRepeatValueFactory);
		spnQtdRepeat.setPrefWidth(80); // alterando a largura

		chbRepeatOptions = new ChoiceBox<>();
		// populando a caixa de escolha
		chbRepeatOptions.setItems(FXCollections.observableArrayList("dia", "semana", "mês", "ano"));
		chbRepeatOptions.getSelectionModel().select(1); // definindo o segundo item da lista como o padrao

		// criando checkbox para todos os dias da semana
		cbxDom = new CheckBox("Domingo");
		cbxSeg = new CheckBox("Segunda");
		cbxTer = new CheckBox("Terça");
		cbxQua = new CheckBox("Quarta");
		cbxQui = new CheckBox("Quinta");
		cbxSex = new CheckBox("Sexta");
		cbxSab = new CheckBox("Sábado");

		HBox hbDayOfWeek = new HBox(); // hbox posiciona todos os itens na horizontal
		hbDayOfWeek.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab);
		hbDayOfWeek.setSpacing(10); // altera o espaco entre os itens do hbox

		cbxSab = new CheckBox("S�bado");

		togEndRepeat = new ToggleGroup();
		radNever = new RadioButton("Nunca");
		radNever.setToggleGroup(togEndRepeat);
		radNever.setSelected(true);
		radOn = new RadioButton("Em");
		radOn.setToggleGroup(togEndRepeat);
		radAfter = new RadioButton("Após");
		radAfter.setToggleGroup(togEndRepeat);

		Calendar calendar = Calendar.getInstance();

		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); // instanciando classe que formata a data em
																		// string
		calendar.add(Calendar.MONTH, 1);

		LocalDate localDateRepeat = LocalDate.parse(dateFormater.format(calendar.getTime())); //// criando uma data sem
																								//// time-zone

		dtEndRepeatDate = new DatePicker(localDateRepeat); // colocando uma data padrao no componente

		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRRepetir");
		hbRepetir.getChildren().addAll(spnRepeat, chbRepeatOptions);

		HBox hbDiasSemana = new HBox();
		hbDiasSemana.setId("hbDiasSemana");
		hbDiasSemana.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui, cbxSex, cbxSab);

		HBox hbEm = new HBox();
		hbEm.setId("hbEm");
		hbEm.getChildren().addAll(radOn, dtEndRepeatDate);

		HBox hbApos = new HBox();
		hbApos.setId("hbApos");
		hbApos.getChildren().addAll(radAfter, spnQtdRepeat, lblOccurrence);

		VBox vb = new VBox();
		vb.setId("vbRecorrencia");
		vb.getChildren().addAll(lblRecurrence, lblRepeat, hbRepetir, hbDiasSemana, lblEndRepeat, radNever, hbEm,
				hbApos);

		return vb;
	}
}

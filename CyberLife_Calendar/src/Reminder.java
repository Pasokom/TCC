import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import db.Database;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Reminder extends Stage {
	
	private Database database;
	
	private Label lblTitle;
	private Label lblSchedule;
	private Label lblDate;
	private Label lblTime;
	private Label lblRecurrence;
	private Label lblRepeat;
	private Label lblEndRepeat;
	private Label lblOccurrence;
	
	private TextField txtName;
	private TextArea txtDescription;
	
	private TimePicker txtTime;
	
	private DatePicker dtDate;
	private DatePicker dtEndRepeatDate;
	
	private CheckBox cbxAllDay;
	private CheckBox cbxRepeat;
	
	private Spinner<Integer> spnRepeat;
	private Spinner<Integer> spnQtdRepeat;
	
	private ChoiceBox<String> chbRepeatOptions;
	
	private CheckBox cbxDom;
	private CheckBox cbxSeg;
	private CheckBox cbxTer;
	private CheckBox cbxQua;
	private CheckBox cbxQui;
	private CheckBox cbxSex;
	private CheckBox cbxSab;
	
	private ToggleGroup togEndRepeat;
	private RadioButton radNever;
	private RadioButton radOn;
	private RadioButton radAfter;
	
	private Button btnEnviar;
	
	public Reminder(Database database) {
		
		this.database = database; //recebendo o banco de dados do construtor
		
		//instanciando componentes do layout
		lblTitle = new Label("Adicionar lembrete");
		lblTitle.setFont(new Font(20));
		lblSchedule = new Label("Horário:");
		lblDate = new Label("Data:");
		lblTime = new Label("Hora:");
		lblRecurrence = new Label("Recorrência:");
		lblRepeat = new Label("Repetir a cada");
		lblEndRepeat = new Label("Termina");
		lblOccurrence = new Label("ocorrência");
		
		cbxAllDay = new CheckBox("Dia inteiro");
		cbxRepeat = new CheckBox("Repetir");
		
		txtName = new TextField();
		txtName.setPromptText("Nome"); //colocando texto que aparece quando o usuario ainda nao digitou
		txtName.setPrefSize(100, 40); //mudando tamanho da caixa de texto
		txtName.setFont(new Font(15)); //alterando tamanho da fonte
		
		txtDescription = new TextArea();
		txtDescription.setPrefSize(400, 150); //mudando tamanho da caixa de texto
		txtDescription.setPromptText("Adicionar descrição"); //colocando texto que aparece quando o usuario ainda nao digitou
		txtTime = new TimePicker();
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); //instanciando classe que formata a data em string
		Date currentDate = new Date(); //criando uma nova data
		LocalDate localDate = LocalDate.parse(dateFormater.format(currentDate)); //criando uma data sem time-zone
		currentDate.setMonth(currentDate.getMonth() + 1); //adicionando um mes na data atual
		LocalDate localDateRepeat = LocalDate.parse(dateFormater.format(currentDate)); ////criando uma data sem time-zone
				
		dtDate = new DatePicker(localDate); //colocando data padrao no componente
		dtEndRepeatDate = new DatePicker(localDateRepeat); //colocando uma data padrao no componente
		
		//criando dois spinners de 1 a 100 de 1 em 1
		SpinnerValueFactory<Integer> repeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		SpinnerValueFactory<Integer> qtdRepeatValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
		
		spnRepeat = new Spinner<>();
		spnRepeat.setValueFactory(repeatValueFactory);
		spnRepeat.setPrefWidth(80); //alterando a largura
		
		spnQtdRepeat = new Spinner<>();
		spnQtdRepeat.setValueFactory(qtdRepeatValueFactory);
		spnQtdRepeat.setPrefWidth(80); //alterando a largura
		
		chbRepeatOptions = new ChoiceBox<>();
		//populando a caixa de escolha
		chbRepeatOptions.setItems(FXCollections.observableArrayList(
				"dia", "semana", "mês", "ano")
			);
		chbRepeatOptions.getSelectionModel().select(1); //definindo o segundo item da lista como o padrao
		
		//criando checkbox para todos os dias da semana
		cbxDom = new CheckBox("Domingo");
		cbxSeg = new CheckBox("Segunda");
		cbxTer = new CheckBox("Terça");
		cbxQua = new CheckBox("Quarta");
		cbxQui = new CheckBox("Quinta");
		cbxSex = new CheckBox("Sexta");
		cbxSab = new CheckBox("Sábado");
		
		HBox hbDayOfWeek = new HBox(); //hbox posiciona todos os itens na horizontal
		hbDayOfWeek.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui,cbxSex,cbxSab);
		hbDayOfWeek.setSpacing(10); //altera o espaco entre os itens do hbox
		
		togEndRepeat = new ToggleGroup();
		radNever = new RadioButton("Nunca");
		radNever.setToggleGroup(togEndRepeat);
		radNever.setSelected(true);
		radOn = new RadioButton("Em");
		radOn.setToggleGroup(togEndRepeat);
		radAfter = new RadioButton("Após");
		radAfter.setToggleGroup(togEndRepeat);
		
		btnEnviar = new Button("Salvar");
		btnEnviar.setOnAction(evento -> { 
			
			try {
				// TODO inserir todos os dados do formulario na tabela
				int allDay = cbxAllDay.isSelected() ? 1 : 0;
				int repeat = cbxRepeat.isSelected() ? 1 : 0;
				database.queryLembrete(txtName.getText(), txtDescription.getText(),dtDate.getValue(), repeat); //fazendo um insert no banco de dados
			} catch (SQLException e) {
				e.printStackTrace();
		} });
		
		//posicionando totos os componentes
		GridPane pnlLayout = new GridPane();
		pnlLayout.setPadding(new Insets(10));
		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);
		pnlLayout.add(lblTitle, 0, 0, 2, 1);
		pnlLayout.add(txtName, 0, 1, 4, 1);
		pnlLayout.add(btnEnviar, 4, 1, 2, 1);
		pnlLayout.add(txtDescription, 0, 2, 4, 4);
		pnlLayout.add(lblSchedule, 4, 2);
		pnlLayout.add(lblDate, 4, 3);
		pnlLayout.add(dtDate, 5, 3, 2, 1);
		pnlLayout.add(lblTime, 4, 4);
		pnlLayout.add(txtTime, 5, 4, 2, 1);
		pnlLayout.add(cbxAllDay, 4, 5, 2, 1);
		pnlLayout.add(cbxRepeat, 6, 5);
		pnlLayout.add(lblRecurrence, 0, 6);
		pnlLayout.add(lblRepeat, 0, 7);
		pnlLayout.add(spnRepeat, 0, 8, 1, 1);
		pnlLayout.add(chbRepeatOptions, 1, 8);
		pnlLayout.add(hbDayOfWeek, 0, 9, 7, 1);
		pnlLayout.add(lblEndRepeat, 0, 10);
		pnlLayout.add(radNever, 0, 11);
		pnlLayout.add(radOn, 0, 12);
		pnlLayout.add(dtEndRepeatDate, 1, 12, 2, 1);
		pnlLayout.add(radAfter, 0, 13);
		pnlLayout.add(spnQtdRepeat, 1, 13);
		pnlLayout.add(lblOccurrence, 2, 13);
		
		Scene scene = new Scene(pnlLayout);
		this.setScene(scene);
		this.show();
	}
}

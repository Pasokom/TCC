import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Reminder extends Stage {
	
	private Label lblTitle;
	private Label lblDate;
	private Label lblTime;
	private Label lblRepetir;
	
	private TextField txtName;
	private TextArea txtDescription;
	private TextField txtTime;
	
	private DatePicker dtDate;
	
	private CheckBox lblAllDay;
	
	private CheckBox cbxDom;
	private CheckBox cbxSeg;
	private CheckBox cbxTer;
	private CheckBox cbxQua;
	private CheckBox cbxQui;
	private CheckBox cbxSex;
	private CheckBox cbxSab;
	
	private Button btnEnviar;
	
	public Reminder() {
		
		lblTitle = new Label("Adicionar lembrete");
		lblTitle.setFont(new Font(20));
		lblDate = new Label("Data:");
		lblTime = new Label("Hora:");
		lblAllDay = new CheckBox("Dia inteiro");
		lblRepetir = new Label("Repetir:");
		
		txtName = new TextField();
		txtName.setPromptText("Nome");
		txtName.setPrefSize(100, 40);
		txtName.setFont(new Font(15));
		
		txtDescription = new TextArea();
		txtDescription.setPrefSize(400, 150);
		txtDescription.setPromptText("Adicionar descrição");
		txtTime = new TextField();
		
		dtDate = new DatePicker();
		
		cbxDom = new CheckBox("Domingo");
		cbxSeg = new CheckBox("Segunda");
		cbxTer = new CheckBox("Terça");
		cbxQua = new CheckBox("Quarta");
		cbxQui = new CheckBox("Quinta");
		cbxSex = new CheckBox("Sexta");
		cbxSab = new CheckBox("Sábado");
		
		HBox hbDayOfWeek = new HBox();
		hbDayOfWeek.getChildren().addAll(cbxDom, cbxSeg, cbxTer, cbxQua, cbxQui,cbxSex,cbxSab);
		hbDayOfWeek.setSpacing(10);
		
		btnEnviar = new Button("Salvar");
		
		GridPane pnlLayout = new GridPane();
		pnlLayout.setPadding(new Insets(10));
		pnlLayout.setVgap(10);
		pnlLayout.setHgap(10);
		pnlLayout.add(lblTitle, 0, 0);
		pnlLayout.add(txtName, 0, 1, 1, 1);
		pnlLayout.add(btnEnviar, 1, 1);
		pnlLayout.add(txtDescription, 0, 2, 1, 3);
		pnlLayout.add(lblDate, 1, 2);
		pnlLayout.add(dtDate, 2, 2);
		pnlLayout.add(lblTime, 1, 3);
		pnlLayout.add(txtTime, 2, 3);
		pnlLayout.add(lblAllDay, 1, 4, 2, 1);
		pnlLayout.add(lblRepetir, 0, 5);
		pnlLayout.add(hbDayOfWeek, 0, 6, 3, 1);
		
		
		Scene scene = new Scene(pnlLayout);
		this.setScene(scene);
		this.show();
	}
}

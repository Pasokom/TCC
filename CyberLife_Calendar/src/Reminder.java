import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Reminder extends Stage {

	private Label lblDate;
	private Label lblTime;
	private Label lblRepetir;
	
	private TextField txtName;
	private TextArea txtDescription;
	private TextField txtTime;
	
	private DatePicker dtDate;
	
	private ToggleButton lblAllDay;
	private Button btnEnviar;
	
	public Reminder() {
		
		lblDate = new Label("Data:");
		lblTime = new Label("Hora:");
		lblAllDay = new ToggleButton("Dia inteiro");
		lblRepetir = new Label("Repetir:");
		
		txtName = new TextField();
		txtName.setPromptText("Nome");
		txtDescription = new TextArea();
		txtDescription.setPromptText("Descrição...");
		txtTime = new TextField();
		
		dtDate = new DatePicker();
		
		
		
		btnEnviar = new Button("Lembrar");
		
		GridPane pnlLayout = new GridPane();
		pnlLayout.add(txtName, 0, 0);
		pnlLayout.add(txtDescription, 0, 1, 1, 3);
		pnlLayout.add(lblDate, 1, 1);
		pnlLayout.add(dtDate, 2, 1);
		pnlLayout.add(lblTime, 1, 2);
		pnlLayout.add(txtTime, 2, 2);
		pnlLayout.add(lblAllDay, 1, 3);
		pnlLayout.add(lblRepetir, 0, 4);
		
		Scene scene = new Scene(pnlLayout);
		this.setScene(scene);
		this.show();
	}
}

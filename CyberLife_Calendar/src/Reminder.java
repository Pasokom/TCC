import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.mysql.cj.xdevapi.Statement;

import component.Recurrence;
import component.TimePickerList;
import db.Database;
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
	private TimePickerList timePickerList;
	
	public Reminder() { 
		super(new HBox());
		
		lblDate = new Label("Data:");
		
		recurrence = new Recurrence();
		recurrence.setDisable(true);
		
		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setPadding(new Insets(20,35,50,35));
		vb.getChildren().addAll(lembrete(recurrence), recurrence);
		
		/* scene */ this.getStylesheets().add(this.getClass().getResource("css/estilo.css").toExternalForm());
			
		this.setRoot(vb);
	}
	
	private VBox lembrete(VBox recorrencia) {
		
		VBox vb = new VBox();
		vb.setSpacing(20);
		
		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");
		
		txtName = new TextField();
		txtName.setPromptText("Título do lembrete");
		txtName.setId("lNome");
		btnEnviar = new Button("Salvar");
		btnEnviar.setId("btnEnviar");
		btnEnviar.setOnAction(evento -> { 
			
			try {
				queryReminder();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		barraTitulo.getChildren().addAll(txtName, btnEnviar);
		
		HBox hbData = new HBox();
		hbData.setId("hbData");
		
		lblDate = new Label("Data:");
		
		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd"); //instanciando classe que formata a data em string
		Date currentDate = new Date(); //criando uma nova data
 		LocalDate localDate = LocalDate.parse(dateFormater.format(currentDate)); //criando uma data sem time-zone
		
		dtDate = new DatePicker(localDate);
		
		hbData.getChildren().addAll(lblDate, dtDate);
		
		timePickerList = new TimePickerList();
		
		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRepetir");
		
		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,Boolean oldValue, Boolean newValue) {
			            
            	timePickerList.setDisable(newValue);
		    }
		});
				
		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,Boolean oldValue, Boolean newValue) {
			            
            	recorrencia.setDisable(!newValue);
		    }
		});
		
		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);
		
		vb.getChildren().addAll(barraTitulo, hbData, hbRepetir, timePickerList);
		
		return vb;
	}
	
	private void queryReminder() throws SQLException {
		
		//		try {
		//		// TODO inserir todos os dados do formulario na tabela
		//		int allDay = cbxAllDay.isSelected() ? 1 : 0;
		//		int repeat = cbxRepeat.isSelected() ? 1 : 0;
		////		database.queryLembrete(txtName.getText(), "",dtDate.getValue(), repeat); //fazendo um insert no banco de dados
		//	} catch (SQLException e) {
		//		e.printStackTrace();
		/*}*/ 
		String queryString = "insert into lembrete()";
		
		Statement cmd = (Statement) Database.getConnection().createStatement();
		
		cmd.execute();
	}
}

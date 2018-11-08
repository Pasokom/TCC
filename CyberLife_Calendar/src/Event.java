import component.Recurrence;
import component.TimePicker;
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

public class Event extends Scene {

	private Label lblStartDate, lblEndDate, lblPlace;
	private TextField txtTitle, txtPlace;
	private TextArea txtDetails;
	private Button btnSave;
	private DatePicker dtStart, dtEnd;
	private TimePicker timeStart, timeEnd;
	private CheckBox cbxAllDay, cbxRepeat;
	private Recurrence recurrence;
	
	public Event() {
		super(new VBox());
		
		/* scene */ this.getStylesheets().add(this.getClass().getResource("css/event.css").toExternalForm());
		
		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setPadding(new Insets(20,35,50,35));
		
		/* barra de titulo*/
		HBox barraTitulo = new HBox();
		barraTitulo.setId("lBarraTitulo");
		
		txtTitle = new TextField();
		txtTitle.setPromptText("Título do evento");
		txtTitle.setId("lNome");
		btnSave = new Button("Salvar");
		btnSave.setId("btnEnviar");
		btnSave.setOnAction(evento -> { 
			
		});
		
		barraTitulo.getChildren().addAll(txtTitle, btnSave);
		/* fim barra de titulo*/
		
		/* barra de data e hora*/
		HBox dateTimeBar = new HBox();
		
		lblStartDate = new Label("De");
		dtStart = new DatePicker();
		timeStart = new TimePicker(false);
		lblEndDate = new Label("até");
		dtEnd = new DatePicker();
		timeEnd = new TimePicker(false);
		
		dateTimeBar.getChildren().addAll(lblStartDate, dtStart, timeStart, lblEndDate, dtEnd, timeEnd);
		/* fim da barra de data e hora*/
		
		/* barra repetir e dia inteiro*/
		HBox hbRepetir = new HBox();
		hbRepetir.setId("hbRepetir");
		
		cbxAllDay = new CheckBox("Dia inteiro");
		cbxAllDay.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,Boolean oldValue, Boolean newValue) {
			            
            	timeStart.setDisable(newValue);
            	timeEnd.setDisable(newValue);
		    }
		});
				
		cbxRepeat = new CheckBox("Repetir");
		cbxRepeat.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,Boolean oldValue, Boolean newValue) {
			            
            	recurrence.setDisable(!newValue);
		    }
		});
		
		hbRepetir.getChildren().addAll(cbxAllDay, cbxRepeat);
		/* fim barra repetir e dia inteiro*/
		
		/* local*/
		HBox hbPlace = new HBox();
		lblPlace = new Label("Local");
		txtPlace = new TextField();
		txtPlace.setPrefWidth(300);
		
		hbPlace.getChildren().addAll(lblPlace, txtPlace);
		/* fim local*/
		
		txtDetails = new TextArea();
		txtDetails.setPromptText("Adicionar detalhes");
		txtDetails.setMaxWidth(580);
		
		recurrence = new Recurrence();
		
		vb.getChildren().addAll(barraTitulo, dateTimeBar, hbRepetir, hbPlace, txtDetails, recurrence);		
		this.setRoot(vb);
	}
}

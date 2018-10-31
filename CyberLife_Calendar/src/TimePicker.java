import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimePicker extends HBox{

	private TextField timeDisplay;
	private Button timeSelector;
	private Stage timeSelectorStage;
	
	public TimePicker() {

		timeDisplay = new TextField();
		timeDisplay.setPrefWidth(50);
		timeSelector = new Button("-");
		
		VBox parent = new VBox();
		Label time = new Label("17:20");
		time.setFont(new Font(40));
		time.setPadding(new Insets(20));
		
		Pane clock = new Pane();
		clock.setPrefSize(200, 200);
		
		Circle circuloPai = new Circle();
		circuloPai.setRadius(90);
		circuloPai.setFill(Color.rgb(0, 0, 0, 0.08));
		circuloPai.setCenterX(100);
		circuloPai.setCenterY(100);
		
		clock.getChildren().add(circuloPai);
		
		Pane[] circulos = new Pane[12];
		
		for (int i = 0; i < 12; i++) {
			
			circulos[i] = new Pane();
			
			Circle circulo = new Circle();
			circulo.setRadius(10);
			circulo.setFill(Color.rgb(0, 0, 0, 0));
			
			int grau = i * 30;
			
			double posX = Math.sin(Math.toRadians(grau)) * 75;
			double posY = Math.cos(Math.toRadians(grau)) * 75;			
			
			circulo.setCenterX(5);
			circulo.setCenterY(7);
			
			Label hora = new Label(String.valueOf(i == 0 ? 12 : i));
			
			circulos[i].setTranslateX(95 + posX - (hora.getPrefWidth() / 2));
			circulos[i].setTranslateY(95 - posY);
			
			circulos[i].getChildren().add(circulo);
			circulos[i].getChildren().add(hora);
			
			circulos[i].setOnMouseEntered(e -> {
				
				((Circle)circulos[grau / 30].getChildren().get(0)).setFill(Color.rgb(0, 0, 255, 0.3));
				
			});
			
			clock.getChildren().add(circulos[i]);
		}
		
		parent.getChildren().addAll(time, clock);
		
		timeSelectorStage = new Stage();
		//timeSelectorStage.initStyle(StageStyle.UNDECORATED);
		timeSelectorStage.setScene(new Scene(parent));
		
		this.getChildren().addAll(timeDisplay, timeSelector);
		
		timeSelector.setOnAction(event -> { timeSelectorStage.showAndWait(); });
	}
	
}

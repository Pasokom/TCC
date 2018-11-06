import java.util.Calendar;

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
	private Button timeDeleter;
	private Stage timeSelectorStage;
	
	private Label hour;
	private Label min;
	
	public TimePicker() {

		timeDisplay = new TextField();
		timeDisplay.setPrefWidth(50);
		timeDeleter = new Button("-");
		
		VBox parent = new VBox();
		
		HBox horario = new HBox();
		horario.setPadding(new Insets(10, 50, 10, 50));
		
		Calendar calendar = Calendar.getInstance();
		
		hour = new Label(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
		Label doisPonto = new Label(":");
		min = new Label(String.format("%02d", calendar.get(Calendar.MINUTE)));
	
		horario.getChildren().addAll(hour, doisPonto, min);
	
		hour.setFont(new Font(40));
		doisPonto.setFont(new Font(40));
		min.setFont(new Font(40));
		
		Pane clock = new Pane();
		clock.setPrefSize(200, 200);
		
		Circle circuloPai = new Circle();
		circuloPai.setRadius(90);
		circuloPai.setFill(Color.rgb(0, 0, 0, 0.08));
		circuloPai.setCenterX(100);
		circuloPai.setCenterY(100);
		
		clock.getChildren().add(circuloPai);
		
		Pane[] circulos = new Pane[24];
		
		for (int i = 0; i < 24; i++) {
			
			int raio = i < 12 ? 55 : 75;
			
			circulos[i] = new Pane();
			
			Circle circulo = new Circle();
			circulo.setRadius(10);
			circulo.setFill(Color.rgb(0, 0, 0, 0));
			
			int grau = (i + 1) * 30;
			
			double posX = Math.sin(Math.toRadians(grau)) * raio;
			double posY = Math.cos(Math.toRadians(grau)) * raio;			
			
			circulo.setCenterX(5);
			circulo.setCenterY(7);
			
			Label hora = new Label(String.valueOf(i + 1 == 24 ? "00" : i + 1));
			
			circulos[i].setTranslateX(95 + posX);
			circulos[i].setTranslateY(95 - posY);
			
			circulos[i].getChildren().add(circulo);
			circulos[i].getChildren().add(hora);
			
			circulos[i].setOnMouseEntered(e -> {
				
				((Circle)circulos[grau / 30 - 1].getChildren().get(0)).setFill(Color.rgb(0, 0, 255, 0.3));
				
			});
			
			circulos[i].setOnMouseExited(e -> {
				
				((Circle)circulos[grau / 30 - 1].getChildren().get(0)).setFill(Color.rgb(0, 0, 0, 0));
			});
			
			circulos[i].setOnMouseClicked(e -> { 
			
				this.hour.setText(String.format("%02d", Integer.parseInt(((Label)circulos[grau / 30 - 1].getChildren().get(1)).getText()))); 
			});
			
			clock.getChildren().add(circulos[i]);
		}
		
		
		timeDisplay.setOnMouseClicked(e -> { timeSelectorStage.showAndWait(); });
		
		parent.getChildren().addAll(horario, clock);
		
		timeSelectorStage = new Stage();
		//timeSelectorStage.initStyle(StageStyle.UNDECORATED);
		timeSelectorStage.setScene(new Scene(parent));
		
		this.getChildren().addAll(timeDisplay, timeDeleter);
		
		timeDeleter.setOnAction(event -> { 
			
			int i;
			
			for(i = 0; i < this.getParent().getChildrenUnmodifiable().size(); i++) {
				
				if(((HBox)this.getParent()).getChildren().get(i) == this) {
					
					break;
				}
			}
			
			if(((HBox)this.getParent()).getChildren().size() > 1) {
			
				((HBox)this.getParent()).getChildren().remove(i);
			}
		});
	}
	
	
	public String get_time_selected() { 
		String time = hour.getText() + "-"  + min.getText(); 
		return time;
	}
	
}

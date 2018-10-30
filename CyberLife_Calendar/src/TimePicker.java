
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TimePicker extends HBox{

	private TextField timeDisplay;
	private Button timeSelector;
	private Stage timeSelectorStage;
	
	public TimePicker() {

		timeDisplay = new TextField();
		timeSelector = new Button("Select");
		
		VBox parent = new VBox();
		Label time = new Label("17:20");
		time.setFont(new Font(40));
		time.setPadding(new Insets(20));
		
		
		Label l1 = new Label("12");
		
		AnchorPane clock = new AnchorPane();
		clock.setPrefSize(125, 100);
		
		AnchorPane.setLeftAnchor(l1, 61d);
		AnchorPane.setRightAnchor(l1, 61d);
		AnchorPane.setTopAnchor(l1, 0d);
		
		clock.getChildren().addAll(l1);
		
		parent.getChildren().addAll(time, clock);
		
		timeSelectorStage = new Stage();
		//timeSelectorStage.initStyle(StageStyle.UNDECORATED);
		timeSelectorStage.setScene(new Scene(parent));
		
		this.getChildren().addAll(timeDisplay, timeSelector);
		
		timeSelector.setOnAction(event -> { timeSelectorStage.showAndWait(); });
	}
	
}

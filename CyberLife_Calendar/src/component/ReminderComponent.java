package component;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ReminderComponent extends VBox{

	public Label lblDay, lblHour, lblReminderTitle;
	
	private Button bntDelete;
	
	public ReminderComponent() { 
		
		this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());
		this.setId("this");
		
		this.lblDay = new Label();
		this.lblHour = new Label();
		this.lblHour.setId("hora");
		this.lblReminderTitle = new Label();
		
		this.bntDelete = new Button("falta o icone");
		
		VBox vCard = new VBox();
		
		VBox vCardHeader = new VBox();
		vCardHeader.setId("card");
		vCardHeader.setPrefWidth(300);

		vCardHeader.getChildren().addAll(lblReminderTitle, lblHour);
		
		VBox vHidden = new VBox();
		vHidden.setId("hidden");
		
		HBox horarios = new HBox();
		horarios.getChildren().addAll(new ToggleButton("asd"));
		
		vHidden.getChildren().addAll(new Label("Sex - 20/10 - 17:20"), horarios);
		vHidden.setPrefWidth(300);
		
		vCard.getChildren().add(vCardHeader);
		vCard.setEffect(new DropShadow(2, Color.BLACK));
		
//		vLabels.setAlignment(Pos.CENTER);
//		this.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setHgrow(this, Priority.ALWAYS);
		
		this.getChildren().add(vCard);
//		this.getChildren().add(this.bntDelete);	
		this.setPadding(new Insets(10, 30, 10, 20));
		
		this.setOnMouseClicked(e -> {
			
			vCard.getChildren().add(vHidden);
		});
	}
}
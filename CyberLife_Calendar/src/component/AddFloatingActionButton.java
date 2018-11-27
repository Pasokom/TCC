package component;

import display.Event;
import display.Reminder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddFloatingActionButton extends StackPane {

	private Stage addSelectorStage;
	
	private Circle circulo;
	private Label addImg;
	
	public AddFloatingActionButton() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/add_fab.css").toExternalForm());
		
		Circle circulo = new Circle();
		circulo.setRadius(30);
		circulo.setFill(Color.rgb(242, 255, 25));
		circulo.setCenterX(68);
		circulo.setCenterY(50);
		
		addImg = new Label();
		addImg.setId("image");
		
		addImg.setAlignment(Pos.CENTER);
		
		this.getChildren().addAll(circulo, addImg);
		
		addSelectorStage = addSelectorStageConstructor(); 
		
		this.setOnMouseClicked(e ->{
			
			Point2D point2d = this.addImg.localToScreen(0d,0d);
			
			addSelectorStage.setX(point2d.getX() + circulo.getRadius() + 13);
			addSelectorStage.setY(point2d.getY() - circulo.getRadius());
			
			addSelectorStage.show();
		});
		
		this.setOnMouseEntered(e -> {
			circulo.setFill(Color.rgb(232, 213, 11));
		});
		
		this.setOnMouseExited(e -> {
			circulo.setFill(Color.rgb(242, 255, 25));
		});
	}
	
	private Stage addSelectorStageConstructor() {
		
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					stage.close();
				}
			}

		});
		
		VBox vOptions = new VBox();
		
		Label lblLembrete = new Label("Lembrete");
		Label lblEvento = new Label("Evento");
		lblEvento.prefWidthProperty().bind(stage.widthProperty());
		
		lblLembrete.setOnMouseClicked(e ->{
			Stage st = new Stage();
			
			st.setScene(new Reminder());
			st.show();
		});
		
		lblEvento.setOnMouseClicked(e ->{
			Stage st = new Stage();

			st.setScene(new Event());
			st.show();
		});
		
		vOptions.getChildren().addAll(lblEvento, lblLembrete);
		
		Scene scene = new Scene(vOptions);
		stage.setScene(scene);
		
		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());
		
		return stage;
	}
}

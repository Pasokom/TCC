package component;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	private Stage eventDetails;
	
	public EventComponent() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");
		
		/* instanciando componentes */
		eventDetails = eventDetailsStage();
		
		lbl_hora = new Label();
		lbl_titulo = new Label();
		lbl_titulo.setId("titulo");
		
		HBox card = new HBox();
		card.getChildren().addAll(lbl_titulo, lbl_hora);
		card.setId("card");
		
		this.getChildren().add(card);
		
		/* configurando eventos */
		this.setOnMouseClicked(e ->{
			
			Point2D point2d = this.localToScreen(0d,0d);
			
			eventDetails.setX(point2d.getX() + this.widthProperty().doubleValue());
			eventDetails.setY(point2d.getY());
			
			eventDetails.show();
		});
		
	}

	private Stage eventDetailsStage() {
		
		Stage stage = new Stage();
		stage.setWidth(100);
		stage.setHeight(100);
		
		stage.initStyle(StageStyle.UNDECORATED);
		
		Label label = new Label("oi");
		label.setStyle("fx-background-color: #000000");
		
		Scene scene = new Scene(label);
		stage.setScene(scene);
		
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					stage.close();
				}
			}

		});
		
		return stage;
	}
	
	public Label getLbl_titulo() {
		return lbl_titulo;
	}

	public void setLbl_titulo(Label lbl_titulo) {
		this.lbl_titulo = lbl_titulo;
	}

	public Label getLbl_hora() {
		return lbl_hora;
	}

	public void setLbl_hora(Label lbl_hora) {
		this.lbl_hora = lbl_hora;
	}
	
}

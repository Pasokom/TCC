package component;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	
	public EventComponent() {
		
		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");
		
		lbl_hora = new Label();
		lbl_titulo = new Label();
		
		VBox card = new VBox();
		card.getChildren().addAll(lbl_titulo, lbl_hora);
		card.setId("card");
		
		this.getChildren().add(card);
		
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

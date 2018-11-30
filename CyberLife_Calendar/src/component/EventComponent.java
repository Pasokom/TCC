package component;
import db.pojo.eventPOJO.EventDB;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	private EventInfo eventDetails;
	
	public EventComponent(EventDB eventDB) {
		
		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");
		
		/* instanciando componentes */
		eventDetails = new EventInfo(eventDB);
		
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
			
			eventDetails.setX(point2d.getX() + this.widthProperty().doubleValue() + 10);
			eventDetails.setY(point2d.getY());
			
			eventDetails.show();
		});
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

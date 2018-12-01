package component.event;

import java.util.Calendar;

import db.pojo.eventPOJO.EventDB;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author manoel
 *
 * Componente responsavel por mostrar os eventos na lista de compromissos do dia.
 *
 */
public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	private EventInfo eventDetails;
	
	public EventComponent(EventDB event) {
		
		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");
		
		/* instanciando componentes */
		eventDetails = new EventInfo(event);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getData_inicio());
		
		lbl_hora = new Label(" - " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)));
		lbl_titulo = new Label(event.getTitulo());
		lbl_titulo.setId("titulo");
		
		HBox card = new HBox();
		card.getChildren().add(lbl_titulo);
	
		if(!event.isDia_todo())
			card.getChildren().add(lbl_hora);
			
		card.setId("card");
		
		this.getChildren().add(card);
		
		/* configurando eventos */
		this.setOnMouseClicked(e ->{
			
			/* Pega a localizacao atual do componente em relacao a tela */
			Point2D point2d = this.localToScreen(0d,0d);
			
			eventDetails.setX(point2d.getX() + this.widthProperty().doubleValue() + 10); //posiciona ao lado do componente
			eventDetails.setY(point2d.getY()); //posiciona na mesma altura
			
			eventDetails.show();
		});
	}
}

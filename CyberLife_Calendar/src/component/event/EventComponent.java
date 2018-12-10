package component.event;

import java.util.Calendar;

import db.pojo.eventPOJO.EventDB;
import display.scenes.Event;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author manoel
 *
 *         Componente responsavel por mostrar os eventos na lista de
 *         compromissos do dia.
 *
 */
public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	private ImageView lblEdit;
	private EventInfo eventDetails;

	public EventComponent(EventDB event) {

		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");

		lblEdit = new ImageView();

		Stage st = new Stage();

		lblEdit.setOnMouseClicked(e -> {
			// Colocar a stage de alterar o evento
			st.setScene(new Event(event));// exemplo
			st.show();
		});

		lblEdit.setId("edit");
		lblEdit.setFitWidth(20);
		lblEdit.setPreserveRatio(true);		
		
		/* instanciando componentes */
		eventDetails = new EventInfo(event);

		lblEdit.setVisible(false);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getData_inicio());

		lbl_hora = new Label(calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ String.format("%02d", calendar.get(Calendar.MINUTE)));
				
		lbl_titulo = new Label(event.getTitulo());
		lbl_titulo.setId("titulo");

		HBox card = new HBox();
		card.getChildren().add(lbl_titulo);

		lbl_titulo.prefWidthProperty().bind(card.widthProperty());

		lbl_hora.setMaxWidth(200);
		lbl_hora.setPrefWidth(140);

		if (!event.isDia_todo())
			card.getChildren().add(lbl_hora);

		card.setId("card");

		card.setOnMouseEntered(e -> {
			lblEdit.setVisible(true);
		});

		card.setOnMouseExited(e -> {
			lblEdit.setVisible(false);
		});

		card.getChildren().add(lblEdit);

		this.getChildren().add(card);

		/* configurando eventos */
		lbl_titulo.setOnMouseClicked(e -> {

			/* Pega a localizacao atual do componente em relacao a tela */
			Point2D point2d = this.localToScreen(0d, 0d);

			eventDetails.setX(point2d.getX() + this.widthProperty().doubleValue() + 10); // posiciona ao lado do
																							// componente
			eventDetails.setY(point2d.getY()); // posiciona na mesma altura

			eventDetails.show();
		});
	}
}

package component.reminder;

import java.util.Calendar;

import db.pojo.reminderPOJO.ReminderDB;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 
 * @author manoel
 *
 *         Componente responsavel por mostrar os lembretes na lista de
 *         compromissos do dia.
 *
 */
public class ReminderComponent extends HBox {

	private Label lbl_titulo;
	private Label lbl_hora;

	public ReminderComponent(ReminderDB reminder) {

		this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());
		this.setId("card");

		/* instanciando componentes */
		// reminderDetails = new EventInfo(eventDB);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(reminder.getlReminderSchedule().get(0).getDatetime_begin());

		lbl_hora = new Label(" - " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ String.format("%02d", calendar.get(Calendar.MINUTE)));
		lbl_titulo = new Label(reminder.getTitle());
		lbl_titulo.setId("titulo");

		this.getChildren().add(lbl_titulo);
		this.getChildren().add(lbl_hora);

		this.setId("card");

		/* configurando eventos */
		this.setOnMouseClicked(e -> {

			// /* Pega a localizacao atual do componente em relacao a tela */
			// Point2D point2d = this.localToScreen(0d,0d);
			//
			// eventDetails.setX(point2d.getX() + this.widthProperty().doubleValue() + 10);
			// //posiciona ao lado do componente
			// eventDetails.setY(point2d.getY()); //posiciona na mesma altura
			//
			// eventDetails.show();
		});
	}
}

package component.Info;

import java.util.ArrayList;

import db.pojo.eventPOJO.EventDB;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author Manoel Mostra para usuario os dias da semana que seus compromissos
 *         irão repetir
 */
public class RecurrenceWeek extends HBox {

    private Label segunda;
	private Label terca;
	private Label quarta;
	private Label quinta;
	private Label sexta;
	private Label sabado;
	private Label domingo;

    public RecurrenceWeek(EventDB event) {
        
        segunda = new Label("S");
		terca = new Label("T");
		quarta = new Label("Q");
		quinta = new Label("Q");
		sexta = new Label("S");
		sabado = new Label("S");
        domingo = new Label("D");
        
        ArrayList<Label> lblDaysOfWeek = new ArrayList<>();
		
		lblDaysOfWeek.add(domingo);
		lblDaysOfWeek.add(segunda);
		lblDaysOfWeek.add(terca);
		lblDaysOfWeek.add(quarta);
		lblDaysOfWeek.add(quinta);
		lblDaysOfWeek.add(sexta);
        lblDaysOfWeek.add(sabado);
        
        for(int i = 0; i < event.getHorario_evento().getDias_semana().length; i++) {
			if(event.getHorario_evento().getDias_semana()[i]) {
				lblDaysOfWeek.get(i).setId("marcado");
			}
        }
        
        this.getChildren().addAll(domingo, segunda, terca, quarta, quinta, sexta, sabado);
		this.setSpacing(15);
    }

    //TODO construtor que recebe o lembrete(ReminderDB)
}
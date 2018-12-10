package component.event;

import java.text.Format;
import java.text.SimpleDateFormat;

import component.Info.RecurrenceInfo;
import component.Info.RecurrenceWeek;
import db.pojo.eventPOJO.EventDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EventInfo extends Stage {

	private Label lblTitulo;
	private Label dtInicio;
	private Label dtFim;
	private Label lclEvent;
	private Label descricao;
	private Label finalRepeticao;

	public EventInfo(EventDB eventDB) {

		GridPane gp = new GridPane();

		Scene scene = new Scene(gp);
		this.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/EventInfo.css").toExternalForm());
		gp.setId("this");
		
		finalRepeticao = new Label();
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Format hour = new SimpleDateFormat("dd/MM/yyyy");

		String data = hour.format(eventDB.getData_inicio());
		String dataFim = hour.format(eventDB.getData_fim());

		data = formatter.format(eventDB.getData_inicio());
		dataFim = formatter.format(eventDB.getData_fim());

		if (eventDB.isDia_todo()) {
			data = hour.format(eventDB.getData_inicio());
			dataFim = hour.format(eventDB.getData_fim());
		}

		lblTitulo = new Label("Título: " + eventDB.getTitulo().toString());
		dtInicio = new Label("De: " + data);
		dtFim = new Label(" até: " + dataFim);
		lclEvent = new Label("Local: " + eventDB.getLocal_evento());
		descricao = new Label("Descrição: " + eventDB.getDescricao());
		
		RecurrenceInfo rInfo = new RecurrenceInfo(eventDB);
		RecurrenceWeek rWeek = new RecurrenceWeek(eventDB);
		
		String fimRepeticao = "Termina: ";

		switch (eventDB.getTipo_fim_repeticao()) {
		case 0:
			fimRepeticao += "nunca";
			break;
		case 1:
			fimRepeticao += formatter.format(eventDB.getHorario_fim_evento().getDia_fim());
			break;
		case 2:
			fimRepeticao += "após " + eventDB.getHorario_fim_evento().getQtd_recorrencias() + " recorrências";
			break;
		default:
			fimRepeticao += "nunca";
			break;
		}

		finalRepeticao.setText(fimRepeticao);
		
		if(eventDB.getTipo_repeticao() != 0) {
			gp.add(rInfo, 0, 5, 2, 1);
		}
		
		if(eventDB.getTipo_repeticao() == 2) {
			gp.add(rWeek, 0, 6, 2, 1);
		}

		if (!eventDB.getDescricao().isEmpty()) {
			gp.add(descricao, 0, 4, 2, 1);
		}

		if (!eventDB.getLocal_evento().isEmpty()) {
			gp.add(lclEvent, 0, 3, 2, 1);
		}

		gp.add(lblTitulo, 0, 0, 2, 1);
		gp.add(dtInicio, 0, 1);
		gp.add(dtFim, 1, 1);
		gp.add(finalRepeticao, 0, 7, 2, 1);

		this.initStyle(StageStyle.UNDECORATED);
		
		this.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					close();
				}
			}

		});
	}
}
package component.event;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.pojo.eventPOJO.EventDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EventInfo extends Stage {

	private Label lblTitulo;
	private Label dtInicio;
	private Label dtFim;
	private Label lclEvent;
	private Label descricao;
	private Label segunda;
	private Label terca;
	private Label quarta;
	private Label quinta;
	private Label sexta;
	private Label sabado;
	private Label domingo;
	private Label finalRepeticao;
	
	public EventInfo(EventDB eventDB) {
		
		GridPane gp = new GridPane();

		Scene scene = new Scene(gp);
		this.setScene(scene);
		
		scene.getStylesheets().add(this.getClass().getResource("/css/eventInfo.css").toExternalForm());
		gp.setId("this");
		
		segunda = new Label("S");
		terca = new Label("T");
		quarta = new Label("Q");
		quinta = new Label("Q");
		sexta = new Label("S");
		sabado = new Label("S");
		domingo = new Label("D");
		
		finalRepeticao = new Label();
		
		ArrayList<Label> lblDaysOfWeek = new ArrayList<>();
		
		lblDaysOfWeek.add(domingo);
		lblDaysOfWeek.add(segunda);
		lblDaysOfWeek.add(terca);
		lblDaysOfWeek.add(quarta);
		lblDaysOfWeek.add(quinta);
		lblDaysOfWeek.add(sexta);
		lblDaysOfWeek.add(sabado);
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Format hour = new SimpleDateFormat("dd/MM/yyyy");
		
		String data = hour.format(eventDB.getData_inicio());
		String dataFim = hour.format(eventDB.getData_fim());

		data = formatter.format(eventDB.getData_inicio());
		dataFim = formatter.format(eventDB.getData_fim());
		
		if(eventDB.isDia_todo()) {
			data = hour.format(eventDB.getData_inicio());
			dataFim = hour.format(eventDB.getData_fim());
		}		

		lblTitulo = new Label("T�tulo: " + eventDB.getTitulo().toString());
		dtInicio = new Label("De: " + data);
		dtFim = new Label(" at�: " + dataFim);
		lclEvent = new Label("Local: " + eventDB.getLocal_evento());
		descricao = new Label("Descri��o: " + eventDB.getDescricao());

		HBox hBox = new HBox();

		hBox.getChildren().addAll(domingo, segunda, terca, quarta, quinta, sexta, sabado);
		hBox.setSpacing(15);
		
		int hr_intervalo = eventDB.getHorario_evento().getIntervalo();
		String tipoRepeticao = "A cada " + eventDB.getHorario_evento().getIntervalo();
			
		switch (eventDB.getTipo_repeticao()) {
		
		case 1:
			if(hr_intervalo > 1)
				tipoRepeticao += " dias";
			else 
				tipoRepeticao = "Todo dia";
			break;
		case 2:
			if(hr_intervalo > 1)
				tipoRepeticao += " semanas";
			else 
				tipoRepeticao = "Toda semana";
			break;
		case 3:
			if(hr_intervalo > 1)
				tipoRepeticao += " meses";
			else 
				tipoRepeticao = "Todo m�s";
			break;
		case 4:
			if(hr_intervalo > 1)
				tipoRepeticao += " anos";
			else 
				tipoRepeticao = "Todo ano";
			break;
		default:
			tipoRepeticao = "";
			break;
		}
		
		Label lblTipoRepeticao = new Label(tipoRepeticao);
		
		String fimRepeticao = "Termina: ";
		
		switch (eventDB.getTipo_fim_repeticao()) {
		case 0:
			fimRepeticao += "nunca";
			break;
		case 1:
			fimRepeticao += formatter.format(eventDB.getHorario_fim_evento().getDia_fim());
			break;
		case 2:
			fimRepeticao += "ap�s " + eventDB.getHorario_fim_evento().getQtd_recorrencias() + " recorr�ncias";
			break;
		default:
			fimRepeticao += "nunca";
			break;
		}
		
		finalRepeticao.setText(fimRepeticao);
		
		if(eventDB.getTipo_repeticao() != 0) {
			gp.add(lblTipoRepeticao, 0, 5, 2, 1);
		}
		
		if(eventDB.getTipo_repeticao() == 2) {
			gp.add(hBox, 0, 6, 2, 1);
		}
		
		if(!eventDB.getDescricao().isEmpty()) {
			gp.add(descricao, 0, 4, 2, 1);
		}
		 
		if(!eventDB.getLocal_evento().isEmpty()) {
			gp.add(lclEvent, 0, 3, 2, 1);
		}
		
		gp.add(lblTitulo, 0, 0, 2, 1);
		gp.add(dtInicio, 0, 1);
		gp.add(dtFim, 1, 1);
		gp.add(finalRepeticao, 0, 7, 2, 1);
		
		this.initStyle(StageStyle.UNDECORATED);

		for(int i = 0; i < eventDB.getHorario_evento().getDias_semana().length; i++) {
			if(eventDB.getHorario_evento().getDias_semana()[i]) {
				lblDaysOfWeek.get(i).setId("marcado");
			}
		}
		
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
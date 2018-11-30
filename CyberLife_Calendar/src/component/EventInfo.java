package component;

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
		
		ArrayList<Label> lblDaysOfWeek = new ArrayList<>();
		
		lblDaysOfWeek.add(domingo);
		lblDaysOfWeek.add(segunda);
		lblDaysOfWeek.add(terca);
		lblDaysOfWeek.add(quarta);
		lblDaysOfWeek.add(quinta);
		lblDaysOfWeek.add(sexta);
		lblDaysOfWeek.add(sabado);
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		String data = formatter.format(eventDB.getData_inicio());
		String dataFim = formatter.format(eventDB.getData_fim());

		lblTitulo = new Label("Título: " + eventDB.getTitulo().toString());
		dtInicio = new Label("De: " + data);
		dtFim = new Label(" até: " + dataFim);
		lclEvent = new Label("Local: " + eventDB.getLocal_evento());
		descricao = new Label("Descrição: " + eventDB.getDescricao());

		HBox hBox = new HBox();

		hBox.getChildren().addAll(domingo, segunda, terca, quarta, quinta, sexta, sabado);
		hBox.setSpacing(15);
		
		if(eventDB.getTipo_repeticao() == 7) {
		gp.add(hBox, 0, 5, 2, 1);
		}
		gp.add(lblTitulo, 0, 0, 2, 1);
		gp.add(dtInicio, 0, 1);
		gp.add(dtFim, 1, 1);
		gp.add(lclEvent, 0, 3, 2, 1);
		gp.add(descricao, 0, 4);

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
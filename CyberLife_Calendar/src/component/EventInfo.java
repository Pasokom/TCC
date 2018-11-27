package component;

import java.text.Format;
import java.text.SimpleDateFormat;

import db.pojo.EventDB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
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
	
	public EventInfo(EventDB eventDB) {
		
		Format formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		String data = formatter.format(eventDB.getData_inicio()); 
		String dataFim = formatter.format(eventDB.getData_fim()); 
		
		lblTitulo = new Label("Título: " + eventDB.getTitulo().toString());
		dtInicio = new Label("De: " + data);
		dtFim = new Label(" até: " + dataFim);
		lclEvent = new Label("Local: " + eventDB.getLocal_evento());
		descricao = new Label("Descrição: " + eventDB.getDescricao());
		
		GridPane gp = new GridPane();
		
		gp.add(lblTitulo, 0, 0, 2, 1);
		gp.add(dtInicio, 0, 1);
		gp.add(dtFim, 1, 1);
		gp.add(lclEvent, 0, 3, 2, 1);
		gp.add(descricao, 0, 4, 2, 1);
		
		this.initStyle(StageStyle.UNDECORATED);

		Scene scene = new Scene(gp);
		this.setScene(scene);
		
		scene.getStylesheets().add(this.getClass().getResource("/css/eventInfo.css").toExternalForm());
		gp.setId("this");
		
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

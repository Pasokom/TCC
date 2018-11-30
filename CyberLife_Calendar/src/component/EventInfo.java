package component;

import java.text.Format;
import java.text.SimpleDateFormat;

import db.pojo.EventDB;
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

		segunda = new Label("S");
		terca = new Label("T");
		quarta = new Label("Q");
		quinta = new Label("Q");
		sexta = new Label("S");
		sabado = new Label("S");
		domingo = new Label("D");

		Format formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		String data = formatter.format(eventDB.getData_inicio());
		String dataFim = formatter.format(eventDB.getData_fim());

		lblTitulo = new Label("T�tulo: " + eventDB.getTitulo().toString());
		dtInicio = new Label("De: " + data);
		dtFim = new Label(" at�: " + dataFim);
		lclEvent = new Label("Local: " + eventDB.getLocal_evento());
		descricao = new Label("Descri��o: " + eventDB.getDescricao());

		HBox hBox = new HBox();

		hBox.getChildren().addAll(domingo, segunda, terca, quarta, quinta, sexta, sabado);
		hBox.setSpacing(15);

		GridPane gp = new GridPane();

		gp.add(hBox, 0, 5, 2, 1);

		gp.add(lblTitulo, 0, 0, 2, 1);
		gp.add(dtInicio, 0, 1);
		gp.add(dtFim, 1, 1);
		gp.add(lclEvent, 0, 3, 2, 1);
		gp.add(descricao, 0, 4);

		this.initStyle(StageStyle.UNDECORATED);

		Scene scene = new Scene(gp);
		this.setScene(scene);

//		scene.getStylesheets().add(this.getClass().getResource("/css/eventInfo.css").toExternalForm());
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
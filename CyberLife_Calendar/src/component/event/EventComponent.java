package component.event;

import java.util.Calendar;
import java.util.Optional;

import db.pojo.eventPOJO.EventDB;
import display.scenes.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author manoel
 *
 *         Componente responsavel por mostrar os eventos na lista de
 *         compromissos do dia.
 * @param <ButtonType>
 *
 */
public class EventComponent extends VBox {

	private Label lbl_titulo;
	private Label lbl_hora;
	private ImageView lblEdit;
	private EventInfo eventDetails;
	private Stage profileSelector;
	private EventDB event;

	public EventComponent(EventDB event) {

		this.getStylesheets().add(this.getClass().getResource("/css/eventComponent.css").toExternalForm());
		this.setId("this");

		lblEdit = new ImageView();

		this.event = event;

		lblEdit.setId("edit");
		lblEdit.setFitWidth(20);
		lblEdit.setPreserveRatio(true);

		/* instanciando componentes */
		eventDetails = new EventInfo(event);

		lblEdit.setVisible(false);

		profileSelector = profileSelectorStageConstructor();

		lblEdit.setOnMouseClicked(e -> {

			Point2D point = lblEdit.localToScreen(0d, 0d);

			profileSelector.setX(point.getX() + 35);
			profileSelector.setY(point.getY());

			profileSelector.show();
		});

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getData_inicio());

		lbl_hora = new Label(
				calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)));

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

	private Stage profileSelectorStageConstructor() {

		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);

		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					stage.close();
				}
			}

		});

		VBox vOptions = new VBox();

		Label lblEditar = new Label("Editar");
		Label lblExcluir = new Label("Excluir");
		lblEditar.prefWidthProperty().bind(stage.widthProperty());

		// TODO CHANGE THE RECORD TO INACTIVE
		lblExcluir.setOnMouseClicked(e -> {

			VBox root = new VBox();
			root.setPadding(new Insets(10));
			root.setSpacing(10);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Excluir!");
			alert.setHeaderText("Seu evento ser� excluido e n�o poder� mais ser recuperado!");
			alert.setContentText("Deseja realmente excluir seu evento?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				System.out.println("Excluido");
			}
		});

		lblEditar.setOnMouseClicked(e -> {
			Stage st = new Stage();
			st.setScene(new Event(this.event, st));
			st.show();
		});

		vOptions.getChildren().addAll(lblEditar, lblExcluir);

		Scene scene = new Scene(vOptions);
		stage.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());

		return stage;
	}
}

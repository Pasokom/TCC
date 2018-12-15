package component.reminder;

import java.util.Calendar;
import java.util.Optional;

import db.functions.reminderFUNCTIONS.ManageReminder;
import db.pojo.reminderPOJO.ReminderDB;
import display.scenes.HomePage;
import display.scenes.Reminder;
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
 *         Componente responsavel por mostrar os lembretes na lista de
 *         compromissos do dia.
 *
 */
public class ReminderComponent extends HBox {

	private ImageView lblEdit;
	private Label lbl_titulo;
	private Label lbl_hora;
	private ReminderInfo reminderDetails;
	private Stage profileSelector;
	private ReminderDB reminder;

	public ReminderComponent(ReminderDB reminder) {
		this.reminder = reminder;

		this.getStylesheets().add(this.getClass().getResource("/css/reminder_component.css").toExternalForm());
		this.setId("card");

		this.reminder = reminder;

		lblEdit = new ImageView();

		lblEdit.setId("edit");
		lblEdit.setFitWidth(20);
		lblEdit.setPreserveRatio(true);

		/* instanciando componentes */

		lblEdit.setVisible(false);

		profileSelector = profileSelectorStageConstructor();

		lblEdit.setOnMouseClicked(e -> {

			Point2D point = lblEdit.localToScreen(0d, 0d);

			profileSelector.setX(point.getX() + 35);
			profileSelector.setY(point.getY());

			profileSelector.show();
		});

		/* instanciando componentes */
		// reminderDetails = new EventInfo(eventDB);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(reminder.getlReminderSchedule().get(0).getDatetime_begin());

		lbl_hora = new Label(
				calendar.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)));

		lbl_hora.setMaxWidth(200);
		lbl_hora.setPrefWidth(140);

		lbl_titulo = new Label(reminder.getTitle());
		lbl_titulo.setId("titulo");

		lbl_titulo.prefWidthProperty().bind(this.widthProperty());

		this.getChildren().add(lbl_titulo);

		if (reminder.getRepetitionType() != 0)
			this.getChildren().add(lbl_hora);

		this.getChildren().add(lblEdit);

		this.setId("card");

		this.setOnMouseEntered(e -> {
			lblEdit.setVisible(true);
		});

		this.setOnMouseExited(e -> {
			lblEdit.setVisible(false);
		});

		reminderDetails = new ReminderInfo(reminder);

		/* configurando eventos */
		lbl_titulo.setOnMouseClicked(e -> {

			/* Pega a localizacao atual do componente em relacao a tela */
			Point2D point2d = this.localToScreen(0d, 0d);

			reminderDetails.setX(point2d.getX() + this.widthProperty().doubleValue() + 10); // posiciona ao lado do
																							// componente
			reminderDetails.setY(point2d.getY()); // posiciona na mesma altura

			reminderDetails.show();
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

		lblExcluir.setOnMouseClicked(e -> {

			VBox root = new VBox();
			root.setPadding(new Insets(10));
			root.setSpacing(10);

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Excluir!");
			alert.setHeaderText("Seu lembrete ser� excluido e n�o poder� mais ser recuperado!");
			alert.setContentText("Deseja realmente excluir seu lembrete?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

			}
		});

		lblEditar.setOnMouseClicked(e -> {
			Stage owner = new Stage();
			owner.setScene(new Reminder(reminder, owner));
			owner.show();
			// TODO abrir tela de lembrete carregando informações dele

		});

		vOptions.getChildren().addAll(lblEditar, lblExcluir);

		Scene scene = new Scene(vOptions);
		stage.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());

		return stage;
	}
}
package component.homepage;

import display.scenes.Event;
import display.scenes.Meta;
import display.scenes.Reminder;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.windows.CloseWindowEsc;

public class AddFloatingActionButton extends StackPane {

	private Popup addSelectorStage;

	private Label addImg;

	public AddFloatingActionButton() {

		this.getStylesheets().add(this.getClass().getResource("/css/add_fab.css").toExternalForm());

		Circle circulo = new Circle();
		circulo.setRadius(25);
		circulo.setFill(Color.rgb(255, 211, 46));
		circulo.setCenterX(68);
		circulo.setCenterY(50);

		addImg = new Label();
		addImg.setId("image");

		addImg.setAlignment(Pos.CENTER);

		this.getChildren().addAll(circulo, addImg);

		addSelectorStage = addSelectorPopup();

		this.setOnMouseClicked(e -> {

			Point2D point2d = this.addImg.localToScreen(0d, 0d);

			addSelectorStage.setX(point2d.getX() + circulo.getRadius() + 14);
			addSelectorStage.setY(point2d.getY() - circulo.getRadius() - 60);

			addSelectorStage.show(this.getScene().getWindow());
		});
		
		this.setOnMouseEntered(e -> {
			circulo.setFill(Color.rgb(232, 213, 11));
		});

		this.setOnMouseExited(e -> {
			circulo.setFill(Color.rgb(242, 255, 25));
		});

		circulo.setId("circulo");
	}

	private Popup addSelectorPopup() {

		Popup popup = new Popup();

		VBox vOptions = new VBox();

		Label lblLembrete = new Label("Lembrete");
		Label lblEvento = new Label("Evento");
		Label lblMeta = new Label("Meta");
		lblEvento.setPrefWidth(120);
		lblLembrete.setPrefWidth(120);
		lblMeta.setPrefWidth(120);

		lblLembrete.setOnMouseClicked(e -> {
			Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(400);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Reminder());
			new CloseWindowEsc(st);
			st.show();
		});

		lblEvento.setOnMouseClicked(e -> {
			Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(400);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Event());
			st.show();
		});
		
		lblMeta.setOnMouseClicked(e -> {
			Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(400);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Meta());
			st.show();
		});

		vOptions.getChildren().addAll(lblEvento, lblLembrete, lblMeta);

		popup.getContent().add(vOptions);
		popup.setAutoHide(true);

		DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

		vOptions.setEffect(shadow);

		vOptions.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());

		return popup;
	}
}

package component;

import java.util.Calendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.CloseWindowEsc;

public class TimePicker extends HBox {

	private TextField timeDisplay;
	private Button timeDeleter;
	private Stage timeSelectorStage;

	private Label hour;
	private Label min;

	final private VBox timeSelector = new VBox();

	private Button btnCancelar;
	private Button btnOK;
	
	private boolean isDeletable;
	
	public TimePicker(boolean isDeletable) {

		this.isDeletable = isDeletable;
		
		timeDisplay = new TextField();
		timeDisplay.setPrefWidth(50);
		timeDisplay.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					timeDisplay.getParent().requestFocus();
				}
			}

		});

		timeDeleter = new Button("-");

		HBox horario = new HBox();
		horario.setPadding(new Insets(10, 50, 10, 50));

		Calendar calendar = Calendar.getInstance();

		hour = new Label(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
		Label doisPonto = new Label(":");
		min = new Label(String.format("%02d", calendar.get(Calendar.MINUTE)));

		horario.getChildren().addAll(hour, doisPonto, min);

		hour.setFont(new Font(40));
		hour.setId("lblSeletor");
		doisPonto.setFont(new Font(40));
		min.setFont(new Font(40));
		min.setId("lblSeletor");

		hour.setOnMouseClicked(e -> {
			timeSelector.getChildren().set(1, horaPane());
		});
		min.setOnMouseClicked(e -> {
			timeSelector.getChildren().set(1, minutoPane());
		});

		timeDisplay.setOnMouseClicked(e -> {

			Point2D point2d = this.localToScreen(0d, 0d);

			timeSelector.getChildren().set(1, horaPane());

			timeSelectorStage.setX(point2d.getX());
			timeSelectorStage.setY(timeDisplay.getHeight() + point2d.getY());

			timeSelector.setOnKeyPressed(e1->{
				if(e1.getCode() == KeyCode.ESCAPE) System.out.println("esc");
				
				new CloseWindowEsc(timeSelectorStage).handle(e1);
			});
			timeSelectorStage.show();
		});

		HBox hbBotoes = new HBox(4);
		hbBotoes.setId("hbBotoes");
		hbBotoes.setAlignment(Pos.CENTER_RIGHT);
		btnCancelar = new Button("Cancelar");
		btnOK = new Button("OK");
		btnOK.setId("btnOK");
		btnOK.setOnAction(e -> {
			change_label();
			timeSelectorStage.close();
		});
		btnCancelar.setOnAction(e -> {
			timeSelectorStage.close();
		});

		hbBotoes.getChildren().addAll(btnCancelar, btnOK);

		timeSelector.getChildren().addAll(horario, horaPane(), hbBotoes);

		timeSelectorStage = new Stage();
		timeSelectorStage.initStyle(StageStyle.UNDECORATED);
		timeSelectorStage.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {

					timeSelectorStage.close();
				}
			}

		});

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(3));
		vBox.setId("container");
		DropShadow shadow = new DropShadow();
		shadow.setWidth(10);
		shadow.setHeight(10);
		timeSelector.setEffect(shadow);
		timeSelector.setId("timeSelector");
		vBox.getChildren().add(timeSelector);

		Scene timeSelectorCena = new Scene(vBox);

		timeSelectorCena.getStylesheets().add(this.getClass().getResource("/css/timepicker.css").toExternalForm());

		timeSelectorStage.setScene(timeSelectorCena);

		this.getChildren().add(timeDisplay);

		if(isDeletable)
			this.getChildren().add(timeDeleter);

		timeDeleter.setOnAction(event -> {
			
			int i;

			for (i = 0; i < this.getParent().getChildrenUnmodifiable().size(); i++) {

				if (((HBox) this.getParent()).getChildren().get(i) == this) {

					break;
				}
			}

			if (((HBox) this.getParent()).getChildren().size() > 1) {

				((HBox) this.getParent()).getChildren().remove(i);
			}
		});

	}

	private Pane horaPane() {

		Pane clock = new Pane();
		clock.setPrefSize(200, 200);

		Circle circuloPai = new Circle();
		circuloPai.setRadius(90);
		circuloPai.setFill(Color.rgb(0, 0, 0, 0.08));
		circuloPai.setCenterX(100);
		circuloPai.setCenterY(100);

		clock.getChildren().add(circuloPai);

		Pane[] circulos = new Pane[24];

		for (int i = 0; i < 24; i++) {

			int raio = i < 12 ? 50 : 75;

			circulos[i] = new Pane();

			Circle circulo = new Circle();
			circulo.setRadius(10);
			circulo.setFill(Color.rgb(0, 0, 0, 0));

			int grau = (i + 1) * 30;

			double posX = Math.sin(Math.toRadians(grau)) * raio;
			double posY = Math.cos(Math.toRadians(grau)) * raio;

			circulo.setCenterX(5);
			circulo.setCenterY(7);

			Label hora = new Label(String.valueOf(i + 1 == 24 ? "00" : i + 1));

			circulos[i].setTranslateX(95 + posX);
			circulos[i].setTranslateY(95 - posY);

			circulos[i].getChildren().add(circulo);
			circulos[i].getChildren().add(hora);

			circulos[i].setOnMouseEntered(e -> {

				((Circle) circulos[grau / 30 - 1].getChildren().get(0)).setFill(Color.rgb(0, 0, 255, 0.3));
			});

			circulos[i].setOnMouseExited(e -> {

				((Circle) circulos[grau / 30 - 1].getChildren().get(0)).setFill(Color.rgb(0, 0, 0, 0));
			});

			circulos[i].setOnMouseClicked(e -> {

				this.hour.setText(String.format("%02d",
						Integer.parseInt(((Label) circulos[grau / 30 - 1].getChildren().get(1)).getText())));
				timeSelector.getChildren().set(1, minutoPane());

			});

			clock.getChildren().add(circulos[i]);
		}

		return clock;
	}

	private Pane minutoPane() {

		Pane clock = new Pane();
		clock.setPrefSize(200, 200);

		Circle circuloPai = new Circle();
		circuloPai.setRadius(90);
		circuloPai.setFill(Color.rgb(0, 0, 0, 0.08));
		circuloPai.setCenterX(100);
		circuloPai.setCenterY(100);

		clock.getChildren().add(circuloPai);

		Pane[] circulos = new Pane[12];

		for (int i = 0; i < 12; i++) {

			int raio = 75;

			circulos[i] = new Pane();

			Circle circulo = new Circle();
			circulo.setRadius(10);
			circulo.setFill(Color.rgb(0, 0, 0, 0));

			int grau = i * 30;

			double posX = Math.sin(Math.toRadians(grau)) * raio;
			double posY = Math.cos(Math.toRadians(grau)) * raio;

			circulo.setCenterX(5);
			circulo.setCenterY(7);

			Label hora = new Label(String.format("%02d", i * 5));

			circulos[i].setTranslateX(95 + posX);
			circulos[i].setTranslateY(95 - posY);

			circulos[i].getChildren().add(circulo);
			circulos[i].getChildren().add(hora);

			circulos[i].setOnMouseEntered(e -> {

				((Circle) circulos[grau / 30].getChildren().get(0)).setFill(Color.rgb(0, 0, 255, 0.3));
			});

			circulos[i].setOnMouseExited(e -> {

				((Circle) circulos[grau / 30].getChildren().get(0)).setFill(Color.rgb(0, 0, 0, 0));
			});

			circulos[i].setOnMouseClicked(e -> {

				this.min.setText(String.format("%02d",
						Integer.parseInt(((Label) circulos[grau / 30].getChildren().get(1)).getText())));
			});

			clock.getChildren().add(circulos[i]);
		}

		return clock;
	}

	public String get_value() {
		return timeDisplay.getText();
	}
	
	public void close_stage() { 
		timeSelectorStage.close();
	}
	
	public void change_label() { 
		timeDisplay.setText(hour.getText() + ":" + min.getText());
	}
	
	public void set_event_ok(EventHandler<ActionEvent> e) {
		this.btnOK.setOnAction(e);
	}
	
	public boolean isDeletable() {
		return isDeletable;
	}
}











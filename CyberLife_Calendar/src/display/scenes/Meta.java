package display.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class Meta extends Scene {

	AnchorPane ap_step1, ap_step2;
	StackPane fab_next;
	TextField txt_titulo;
	Label lbl_sugestions;
	
	Spinner<Integer> spn_qtd;
	Label lbl_qtd;
	TextField txt_minutes;
	Label lbl_minutes;
	Label lbl_schedule;
	ToggleButton btn_morn, btn_after, btn_even;
	StackPane fab_back, fab_done;

	String[] lista = {"Fazer exercícios", "Estudar", "Praticar um instrumento", "oi"};
	
	public Meta() {
		super(new VBox());
		
		this.getStylesheets().add(this.getClass().getResource("../../css/meta.css").toExternalForm());
		
		step1();
		step2();

		this.setRoot(ap_step1);
	}

	private void step1() {

		ap_step1 = new AnchorPane();

		txt_titulo = new TextField();
		txt_titulo.setPromptText("Título");
		txt_titulo.setId("title");

		lbl_sugestions = new Label("Sugestões:");

		Circle circle_next = new Circle(20);
		circle_next.setId("circle_next");

		ImageView img_next = new ImageView();
		img_next.setId("img_next");

		fab_next = new StackPane();
		fab_next.getChildren().addAll(circle_next, img_next);

		fab_next.setOnMouseClicked(e -> {
			this.setRoot(ap_step2);
			this.getRoot().requestFocus();
		});

		VBox vb_content = new VBox();
		vb_content.getStyleClass().add("vbox");
		vb_content.getChildren().addAll(txt_titulo, lbl_sugestions);

		ToggleGroup group = new ToggleGroup();
		
		for (String meta : lista) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}
		
		AnchorPane.setTopAnchor(vb_content, 0d);
		AnchorPane.setRightAnchor(vb_content, 0d);
		AnchorPane.setBottomAnchor(vb_content, 0d);
		AnchorPane.setLeftAnchor(vb_content, 0d);

		AnchorPane.setBottomAnchor(fab_next, 10d);
		AnchorPane.setRightAnchor(fab_next, 10d);

		ap_step1.getChildren().addAll(vb_content, fab_next);
	}

	private void step2() {

		ap_step2 = new AnchorPane();

		spn_qtd = new Spinner<Integer>();
		SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 7, 1);
		spn_qtd.setValueFactory(factory);

		lbl_qtd = new Label("vezes por semana");

		HBox hb_qtd = new HBox();
		hb_qtd.getStyleClass().add("hbox");
		hb_qtd.getChildren().addAll(spn_qtd, lbl_qtd);

		txt_minutes = new TextField("30");
		lbl_minutes = new Label("minutos");

		HBox hb_minutes = new HBox();
		hb_minutes.getStyleClass().add("hbox");
		hb_minutes.getChildren().addAll(txt_minutes, lbl_minutes);

		lbl_schedule = new Label("Horário:");

		ToggleGroup group = new ToggleGroup();

		btn_morn = new ToggleButton("Manhã");
		btn_morn.setToggleGroup(group);
		btn_morn.setSelected(true);
		btn_after = new ToggleButton("Tarde");
		btn_after.setToggleGroup(group);
		btn_even = new ToggleButton("Noite");
		btn_even.setToggleGroup(group);

		HBox hb_buttons = new HBox();
		hb_buttons.getStyleClass().add("hbox");
		hb_buttons.getChildren().addAll(btn_morn, btn_after, btn_even);

		VBox vb_content = new VBox();
		vb_content.getStyleClass().add("vbox");
		vb_content.getChildren().addAll(hb_qtd, hb_minutes, lbl_schedule, hb_buttons);

		Circle circle_back = new Circle(20);
		circle_back.setId("circle_next");

		ImageView img_back = new ImageView();
		img_back.setId("img_back");

		fab_back = new StackPane();
		fab_back.getChildren().addAll(circle_back, img_back);

		fab_back.setOnMouseClicked(e -> {
			this.setRoot(ap_step1);
			this.getRoot().requestFocus();
		});

		Circle circle_done = new Circle(20);
		circle_done.setId("circle_next");

		ImageView img_done = new ImageView();
		img_done.setId("img_done");

		fab_done = new StackPane();
		fab_done.getChildren().addAll(circle_done, img_done);

		fab_done.setOnMouseClicked(e -> {
		});

		AnchorPane.setBottomAnchor(fab_back, 10d);
		AnchorPane.setLeftAnchor(fab_back, 10d);

		AnchorPane.setBottomAnchor(fab_done, 10d);
		AnchorPane.setRightAnchor(fab_done, 10d);

		ap_step2.getChildren().addAll(vb_content, fab_back, fab_done);
	}
}

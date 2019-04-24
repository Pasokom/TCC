package display.scenes;

import component.CustomScroll;
import db.functions.appointment.CreateAppointment;
import db.pojo.goalPOJO.GoalDB;
import javafx.scene.Scene;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import statics.SESSION;

public class Meta extends Scene {

	AnchorPane ap_step1, ap_step2;
	StackPane fab_next;
	TextField txt_titulo;
	
	Spinner<Integer> spn_qtd;
	Label lbl_qtd;
	TextField txt_minutes;
	Label lbl_minutes;
	Label lbl_schedule;
	ToggleButton btn_morn, btn_after, btn_even, btn_any;
	StackPane fab_back, fab_done;

	String[] lista_exercicios = {"Malhar", "Caminhar", "Correr", "Nadar", "Jogar futebol", "Jogar basquete"};
	String[] lista_desenvolver = {"Aprender um idioma", "Praticar um instrumento", "Aprender programação"};
	String[] lista_social = {"Falar com um amigo", "Passear com o cachorro"};
	String[] lista_solitaria = {"Ler", "Assistir a um filme", "Jogar videogame", "Cozinhar", "Escrever um diário"};
	String[] lista_organizacao = {"Limpar", "Planejar o dia", "Limpar a caixa de email", "Comprar mantimentos"};
	
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
		txt_titulo.prefWidthProperty().bind(this.widthProperty().subtract(30));

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

		fab_next.setOnMouseEntered(e -> {
			circle_next.setFill(Color.rgb(0, 126, 140));
		});

		fab_next.setOnMouseExited(e -> {
			circle_next.setFill(Color.rgb(0, 149, 166));
		});

		VBox vb_content = new VBox();
		vb_content.getStyleClass().add("vbox");
		vb_content.getChildren().addAll(txt_titulo);

		vb_content.getChildren().add(new Label("Exercícios"));

		ToggleGroup group = new ToggleGroup();
		
		for (String meta : lista_exercicios) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}

		vb_content.getChildren().add(new Label("Desenvolver habilidades"));

		for (String meta : lista_desenvolver) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}

		vb_content.getChildren().add(new Label("Família e amigos"));

		for (String meta : lista_social) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}

		vb_content.getChildren().add(new Label("Tempo só"));

		for (String meta : lista_solitaria) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}

		vb_content.getChildren().add(new Label("Organizar a vida"));

		for (String meta : lista_organizacao) {
			
			ToggleButton button = new ToggleButton(meta);
			button.setToggleGroup(group);

			button.setOnAction(e -> {
				txt_titulo.setText(button.getText());
			});
			
			vb_content.getChildren().add(button);
		}

		CustomScroll scroll = new CustomScroll();
		scroll.setContent(vb_content);
		
		AnchorPane.setTopAnchor(scroll, 0d);
		AnchorPane.setRightAnchor(scroll, 0d);
		AnchorPane.setBottomAnchor(scroll, 0d);
		AnchorPane.setLeftAnchor(scroll, 0d);

		AnchorPane.setBottomAnchor(fab_next, 10d);
		AnchorPane.setRightAnchor(fab_next, 10d);

		ap_step1.getChildren().addAll(scroll, fab_next);
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
		btn_any = new ToggleButton("A qualquer momento");
		btn_any.setToggleGroup(group);

		HBox hb_buttons = new HBox();
		hb_buttons.getStyleClass().add("hbox");
		hb_buttons.getChildren().addAll(btn_morn, btn_after, btn_even);

		VBox vb_content = new VBox();
		vb_content.getStyleClass().add("vbox");
		vb_content.getChildren().addAll(hb_qtd, hb_minutes, lbl_schedule, hb_buttons, btn_any);

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

		fab_back.setOnMouseEntered(e -> {
			circle_back.setFill(Color.rgb(0, 126, 140));
		});

		fab_back.setOnMouseExited(e -> {
			circle_back.setFill(Color.rgb(0, 149, 166));
		});

		Circle circle_done = new Circle(20);
		circle_done.setId("circle_next");

		ImageView img_done = new ImageView();
		img_done.setId("img_done");

		fab_done = new StackPane();
		fab_done.getChildren().addAll(circle_done, img_done);

		fab_done.setOnMouseClicked(e -> {

			CreateAppointment appointment = new CreateAppointment();
			appointment.create(this.getGoal());
			HomePage.goals.update();
			((Stage)this.getWindow()).close();
		});

		fab_done.setOnMouseEntered(e -> {
			circle_done.setFill(Color.rgb(0, 126, 140));
		});

		fab_done.setOnMouseExited(e -> {
			circle_done.setFill(Color.rgb(0, 149, 166));
		});

		AnchorPane.setBottomAnchor(fab_back, 10d);
		AnchorPane.setLeftAnchor(fab_back, 10d);

		AnchorPane.setBottomAnchor(fab_done, 10d);
		AnchorPane.setRightAnchor(fab_done, 10d);

		ap_step2.getChildren().addAll(vb_content, fab_back, fab_done);
	}

	private GoalDB getGoal() {

 		String titulo = txt_titulo.getText();
 		int qtd_semana = spn_qtd.getValue();
 		int duracao_minutos = Integer.parseInt(txt_minutes.getText());
		int periodo = getPeriodo();
		int fk_usuario = (int)SESSION.get_user_cod(); 

		GoalDB goal = new GoalDB();

		goal.setTitulo(titulo);
		goal.setQtd_semana(qtd_semana);
		goal.setDuracao_minutos(duracao_minutos);
		goal.setPeriodo(periodo);
		goal.setFk_usuario(fk_usuario);

		return goal;
	}

	private int getPeriodo() {

		if (btn_morn.isSelected()) {
			return 1;
		}

		if (btn_after.isSelected()) {
			return 2;
		}

		if (btn_even.isSelected()) {
			return 3;
		}

		return 0;
	}
}

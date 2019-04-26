package component.homepage;

import db.pojo.UserSession;
import display.poupoup.EditProfile;
import display.poupoup.Profile;
import display.scenes.HomePage;
import display.scenes.Login;
import display.scenes.Project;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import statics.SESSION;

public class NavigationMenu extends AnchorPane {

	private Stage profileSelector;
	private EditProfile editProfile;

	public NavigationMenu() {

		editProfile = new EditProfile();

		this.setPrefWidth(250);

		this.getStylesheets().add(this.getClass().getResource("/css/navigation_menu.css").toExternalForm());
		this.setId("this");

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

		/* Conteudo do perfil */
		Circle profileImg = new Circle();
		profileImg.setRadius(20);
		profileImg.setFill(Color.rgb(0, 0, 0, 0.08));
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);

		StackPane userImg = new StackPane();
		Label userInitial = new Label(SESSION.get_user_name().substring(0, 1).toUpperCase());
		userInitial.setFont(new Font(20));
		userImg.getChildren().addAll(profileImg, userInitial);

		userImg.setOnMouseClicked(e -> {

			Profile profile = new Profile();

			Point2D point = userImg.localToScreen(0d, 0d);

			profile.setX(point.getX() + userImg.getHeight());
			profile.setY(point.getY());

			profile.show(this.getScene().getWindow()); 
		});

		profileSelector = profileSelectorStageConstructor();

		/* Botao adicionar */
		AddFloatingActionButton circleButton = new AddFloatingActionButton();

		/* Fim botao adicionar */
		AnchorPane.setTopAnchor(userImg, 10d);
		AnchorPane.setLeftAnchor(userImg, 10d);

		AnchorPane.setBottomAnchor(circleButton, 10d);
		AnchorPane.setRightAnchor(circleButton, 10d);

		VBox vb_items = new VBox();

		/* Opções */
		ToggleGroup grp_options = new ToggleGroup();

		VBox vb_options = new VBox();
		ToggleButton btn_calendar = new ToggleButton("Calendário");
		ToggleButton btn_goals = new ToggleButton("Metas");
		
		btn_calendar.setPrefWidth(230);
		btn_goals.setPrefWidth(230);
		btn_calendar.setAlignment(Pos.CENTER_LEFT);
		btn_goals.setAlignment(Pos.CENTER_LEFT);
		btn_calendar.setId("btn_calendar");
		btn_goals.setId("btn_goals");
		btn_calendar.setGraphicTextGap(15);
		btn_goals.setGraphicTextGap(15);
		btn_calendar.setToggleGroup(grp_options);
		btn_goals.setToggleGroup(grp_options);
		btn_calendar.setSelected(true);

		btn_calendar.setOnAction(e -> {

			HomePage.goals.setVisible(false);
			HomePage.goals.setManaged(false);
		});

		btn_goals.setOnAction(e -> {

			HomePage.goals.setVisible(true);
			HomePage.goals.setManaged(true);
		});

		vb_options.setFillWidth(true);
		vb_options.setSpacing(1);
		vb_options.getChildren().addAll(btn_calendar, btn_goals);

		VBox vb_projects = new VBox();
		Label lbl_projects = new Label("PROJETOS");
		Button btn_add_project = new Button("+ Adicionar projeto");

		btn_add_project.setOnAction(e -> {

			Stage st = new Stage();
			st.setScene(new Project());
			st.show();
		});

		vb_projects.getChildren().addAll(lbl_projects, btn_add_project);
		vb_items.getChildren().addAll(vb_options, vb_projects);

		AnchorPane.setLeftAnchor(vb_items, 0d);
		AnchorPane.setTopAnchor(vb_items, 100d);

		this.getChildren().addAll(userImg, circleButton, vb_items);
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

		Label lblSair = new Label("Sair");
		Label lblSairPro = new Label("Sair e fechar o programa");

		VBox vOptions = new VBox();
		vOptions.getChildren().addAll(lblSair, lblSairPro);

		Scene scene = new Scene(vOptions);
		stage.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());

		lblSair.prefWidthProperty().bind(stage.widthProperty());

		lblSairPro.setOnMouseClicked(e -> {

			UserSession.close();

			stage.close();
			SESSION.END_SESSION();
			Main.main_stage.close();
			/**
			 * falta colocar que se o usuário selecionar sair e fechar programa, parar de
			 * ler o arquivo do "mantenha-me conectado" e iniciar na tela de login na
			 * próxima vez que abrir
			 */
		});

		lblSair.setOnMouseClicked(e -> {
			
			UserSession.close();

			SESSION.END_SESSION();
			Main.main_stage.setScene(new Login());
		});

		return stage;
	}
}
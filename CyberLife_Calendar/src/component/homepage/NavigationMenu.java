package component.homepage;

import java.util.ArrayList;

import component.CustomScroll;
import db.functions.appointment.LoadAppointment;
import db.functions.registrationAndLogin.HandlerLogin;
import db.pojo.UserSession;
import db.pojo.projectPOJO.ProjectDB;
import display.poupoup.EditProfile;
import display.poupoup.Notifications;
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
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Main;
import statics.SESSION;

public class NavigationMenu extends AnchorPane {

	private Stage profileSelector;
	private EditProfile editProfile;
	private ToggleGroup grp_options;
	private VBox vb_projects;
	private Circle profileImg;
	private Label userInitial;
	private StackPane userImg;
	private Label btn_notifications;
	private Label btn_refresh;

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
		
		profileImg = new Circle();
		HandlerLogin handlerLogin = new HandlerLogin();
		/* Conteudo do perfil */
		profileImg.setRadius(25);
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);

		userImg = new StackPane();

		if (handlerLogin.userImageExists((int)SESSION.get_user_cod())){

			Image img = new Image("http://localhost/cyberlife/imagens/img" + SESSION.get_user_cod() + ".jpeg");
			profileImg.setFill(new ImagePattern(img));
			userImg.getChildren().addAll(profileImg);
		}
		else {

			userInitial = new Label(SESSION.get_user_name().substring(0, 1).toUpperCase());
			userInitial.setFont(new Font(20));
			userImg.getChildren().addAll(profileImg, userInitial);
		}

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
		grp_options = new ToggleGroup();

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
			HomePage.project.setVisible(false);
			HomePage.project.setManaged(false);
		});

		btn_goals.setOnAction(e -> {

			HomePage.goals.setVisible(true);
			HomePage.goals.setManaged(true);
			HomePage.project.setVisible(false);
			HomePage.project.setManaged(false);
		});

		vb_options.setFillWidth(true);
		vb_options.setSpacing(1);
		vb_options.getChildren().addAll(btn_calendar, btn_goals);

		CustomScroll cs_projects = new CustomScroll();
		cs_projects.getStyleClass().add("custom-scroll");
		cs_projects.setVbarPolicy(ScrollBarPolicy.NEVER);

		vb_projects = new VBox();

		cs_projects.setContent(vb_projects);

		updateProjects();

		vb_items.getChildren().addAll(vb_options, cs_projects);
		vb_items.setSpacing(5);

		AnchorPane.setLeftAnchor(vb_items, 0d);
		AnchorPane.setTopAnchor(vb_items, 100d);
		AnchorPane.setBottomAnchor(vb_items, 0d);
		AnchorPane.setRightAnchor(vb_items, 0d);

		btn_notifications = new Label();
		btn_notifications.setId("btn_notifications");

		btn_notifications.setOnMouseClicked(e -> {

			Notifications notifications = new Notifications();

			Point2D point2d = this.btn_notifications.localToScreen(0d, 0d);

			notifications.setX(point2d.getX() + this.btn_notifications.getWidth());
			notifications.setY(point2d.getY());

			notifications.setWidth(200);
			notifications.setHeight(200);
			notifications.show(this.getScene().getWindow());
		});

		btn_refresh = new Label();
		btn_refresh.setId("btn_refresh");

		btn_refresh.setOnMouseClicked(e -> {

			HomePage.listCalendar.update(HomePage.listCalendar.getCurrentDate());
			HomePage.calendarComponent.createCalendar(HomePage.calendarComponent.getDate());
			HomePage.menu.update();
			HomePage.project.update();
		});

		AnchorPane.setTopAnchor(btn_notifications, 25d);
		AnchorPane.setLeftAnchor(btn_notifications, 80d);

		AnchorPane.setTopAnchor(btn_refresh, 25d);
		AnchorPane.setLeftAnchor(btn_refresh, 120d);

		this.getChildren().addAll(userImg, btn_notifications, btn_refresh, vb_items, circleButton);
	}

	public void updateProjects() {

		vb_projects.getChildren().clear();

		Label lbl_projects = new Label("PROJETOS");
		lbl_projects.setId("lbl_projects");

		vb_projects.getChildren().add(lbl_projects);

		ArrayList<ProjectDB> projects = new LoadAppointment().loadProjects();

		for (ProjectDB project : projects) {
			
			ProjectButton opt_project = new ProjectButton();
			opt_project.setText(project.getTitulo());
			opt_project.setPrefWidth(230);
			opt_project.setAlignment(Pos.CENTER_LEFT);
			opt_project.setToggleGroup(grp_options);
			opt_project.setProjeto(project);
			vb_projects.getChildren().add(opt_project);

			opt_project.setOnAction(e -> {

				HomePage.project.loadProject(((ProjectButton)e.getSource()).getProjeto());
				HomePage.project.setVisible(true);
				HomePage.project.setManaged(true);
				HomePage.goals.setVisible(false);
				HomePage.goals.setManaged(false);
			});
		}

		Button btn_add_project = new Button("+ Adicionar projeto");

		btn_add_project.setOnAction(e -> {

			Stage st = new Stage();
			st.setWidth(300);
			st.setHeight(300);
			st.initStyle(StageStyle.UTILITY);
			st.initModality(Modality.APPLICATION_MODAL);
			st.setScene(new Project());
			st.show();
		});

		Separator separator = new Separator();

		vb_projects.getChildren().addAll(separator, btn_add_project);
	}

	public void update() {

		/*profileImg = new Circle();
        profileImg.setRadius(50);
		profileImg.setCenterX(100);
        profileImg.setCenterY(100);*/

		HandlerLogin handlerLogin = new HandlerLogin();
		/* Conteudo do perfil */

		userInitial = new Label();

		if (handlerLogin.userImageExists((int)SESSION.get_user_cod())){
		
			Image img = new Image("http://localhost/cyberlife/imagens/img" + SESSION.get_user_cod() + ".jpeg");
			profileImg.setFill(new ImagePattern(img));
		}
		else {

			userInitial.setText(SESSION.get_user_name().substring(0, 1).toUpperCase());
			userInitial.setFont(new Font(20));
		}
		userImg.getChildren().clear();
		userImg.getChildren().addAll(profileImg, userInitial);
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
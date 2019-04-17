package component.homepage;

import db.pojo.UserSession;
import display.poupoup.EditProfile;
import display.poupoup.Profile;
import display.scenes.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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

		AnchorPane.setTopAnchor(userImg, 0d);
		AnchorPane.setLeftAnchor(userImg, 5d);

		AnchorPane.setBottomAnchor(circleButton, 0d);
		AnchorPane.setRightAnchor(circleButton, -20d);

		this.getChildren().addAll(userImg, circleButton);
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
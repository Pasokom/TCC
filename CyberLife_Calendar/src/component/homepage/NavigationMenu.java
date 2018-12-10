package component.homepage;

import display.scenes.Login;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import listeners.IOFunctions;
import main.Main;
import statics.SESSION;

public class NavigationMenu extends AnchorPane {

	private Label lblNome, lblEmail;
	private HBox hProfile;
	private VBox vProfileNameEmail;
	private Stage profileSelector;

	private ImageView ivLogout;

	private IOFunctions ioFunctions;

	public NavigationMenu() {

		ioFunctions = new IOFunctions();

		ioFunctions.getConfigFolder();
		
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

		ivLogout = new ImageView();
		ivLogout.setId("logout");

		ivLogout.setFitWidth(35);
		ivLogout.setPreserveRatio(true);
		/* Conteudo do perfil */
		hProfile = new HBox();

		Circle profileImg = new Circle();
		profileImg.setRadius(20);
		profileImg.setFill(Color.rgb(0, 0, 0, 0.08));
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);

		profileSelector = profileSelectorStageConstructor();

		ivLogout.setOnMouseClicked(e -> {

			Point2D point = ivLogout.localToScreen(0d, 0d);

			profileSelector.setX(point.getX() + 35);
			profileSelector.setY(point.getY());

			profileSelector.show();
		});

		/* VBox do nome e email */
		vProfileNameEmail = new VBox();

		lblNome = new Label(SESSION.get_user_name() + " " + SESSION.get_user_last_name());
		lblEmail = new Label(SESSION.get_user_email());

		vProfileNameEmail.getChildren().addAll(lblNome, lblEmail);
		/* Fim VBox do nome e email */

		hProfile.getChildren().addAll(profileImg, vProfileNameEmail);
		hProfile.getChildren().add(ivLogout);
		/* Fim do conteudo do perfil */

		/* Botao adicionar */
		AddFloatingActionButton circleButton = new AddFloatingActionButton();

		/* Fim botao adicionar */

		AnchorPane.setTopAnchor(ivLogout, 0d);

		AnchorPane.setTopAnchor(hProfile, 0d);
		AnchorPane.setLeftAnchor(hProfile, 0d);

		AnchorPane.setBottomAnchor(circleButton, 0d);
		AnchorPane.setRightAnchor(circleButton, -20d);

		this.getChildren().addAll(hProfile, circleButton);
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

		Label lblSair = new Label("Sair");
		Label lblSairPro = new Label("Sair e fechar o programa");
		lblSair.prefWidthProperty().bind(stage.widthProperty());

		lblSairPro.setOnMouseClicked(e -> {
			System.exit(0);
			/**
			 * falta colocar que se o usuário selecionar sair e fechar programa, parar de
			 * ler o arquivo do "mantenha-me conectado" e iniciar na tela de login na
			 * próxima vez que abrir
			 */
		});

		lblSair.setOnMouseClicked(e -> {
			Main.main_stage.setScene(new Login());
		});

		vOptions.getChildren().addAll(lblSair, lblSairPro);

		Scene scene = new Scene(vOptions);
		stage.setScene(scene);

		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());

		return stage;
	}
}

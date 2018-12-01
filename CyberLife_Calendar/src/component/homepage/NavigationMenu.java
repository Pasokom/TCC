package component.homepage;

import display.Event;
import display.Reminder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import statics.SESSION;

public class NavigationMenu extends AnchorPane {

	private Label lblNome, lblEmail;
	private HBox hProfile;
	private VBox vProfileNameEmail;
	private Stage profileSelector;
	
	public NavigationMenu() {
		
		this.setPrefWidth(250);
		
		this.getStylesheets().add(this.getClass().getResource("/css/navigation_menu.css").toExternalForm());
		this.setId("this");
		
		/* Conteudo do perfil */
		hProfile = new HBox();
		
		Circle profileImg = new Circle();
		profileImg.setRadius(20);
		profileImg.setFill(Color.rgb(0, 0, 0, 0.08));
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);
		
		profileSelector = profileSelectorStageConstructor();
		
		profileImg.setOnMouseClicked(e -> {
			
			Point2D point = profileImg.localToScreen(0d, 0d);
			
			profileSelector.setX(point.getX() + 85);
			profileSelector.setY(point.getY() + 85 + profileImg.getRadius() * 2);
			
			profileSelector.show();
		});
		
		/* VBox do nome e email */
		vProfileNameEmail = new VBox();
		
		lblNome = new Label(SESSION.get_user_name() + " " + SESSION.get_user_last_name());
		lblEmail = new Label(SESSION.get_user_email());
		
		vProfileNameEmail.getChildren().addAll(lblNome, lblEmail);
		/* Fim VBox do nome e email */
		
		hProfile.getChildren().addAll(profileImg, vProfileNameEmail);
		/* Fim do conteudo do perfil */
		
		/* Botao adicionar */
		AddFloatingActionButton circleButton = new AddFloatingActionButton();
		
		/* Fim botao adicionar */
		
		AnchorPane.setTopAnchor(hProfile, 0d);
		AnchorPane.setLeftAnchor(hProfile, 0d);
		AnchorPane.setRightAnchor(hProfile, 0d);
		
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
		
		Label lblPerfil = new Label("Perfil");
		Label lblSair = new Label("Sair");
		lblSair.prefWidthProperty().bind(stage.widthProperty());
		
		lblSair.setOnMouseClicked(e ->{
			Stage st = new Stage();
			st.setScene(new Reminder());
			st.show();
		});
		
		lblPerfil.setOnMouseClicked(e ->{
			Stage st = new Stage();
			st.setScene(new Event());
			st.show();
		});
		
		vOptions.getChildren().addAll(lblPerfil, lblSair);
		
		Scene scene = new Scene(vOptions);
		stage.setScene(scene);
		
		scene.getStylesheets().add(this.getClass().getResource("/css/add_fab_selector.css").toExternalForm());
		
		return stage;
	}
}

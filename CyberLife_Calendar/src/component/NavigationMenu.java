package component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import display.poupoup.ShowPicture;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main.Main;
import statics.SESSION;

public class NavigationMenu extends AnchorPane {

	private Label lblNome, lblEmail;
	private HBox hProfile;
	private VBox vProfileNameEmail;
	private ImageView view;

	public NavigationMenu() {

		this.setPrefWidth(250);

		this.getStylesheets().add(this.getClass().getResource("/css/navigation_menu.css").toExternalForm());
		this.setId("this");

		/* Conteudo do perfil */
		hProfile = new HBox();

		Circle circle = new Circle();
		circle.setRadius(20);
		circle.setFill(Color.rgb(0, 0, 0, 0.08));
		circle.setCenterX(100);
		circle.setCenterY(100);

		/* VBox do nome e email */
		vProfileNameEmail = new VBox();

		lblNome = new Label(SESSION.get_user_name() + " " + SESSION.get_user_last_name());
		lblEmail = new Label(SESSION.get_user_email());

		vProfileNameEmail.getChildren().addAll(lblNome, lblEmail);
		/* Fim VBox do nome e email */

		Image profileImg = SESSION.get_user_image();
		this.view = new ImageView();
		/** size of image view  */
		this.view.setFitWidth(60);
		this.view.setFitHeight(60);

		if (profileImg != null) {
			this.view.setImage(profileImg);
			hProfile.getChildren().addAll(this.view, vProfileNameEmail);
		} else {
			try {
				// don't know why but this shit had to be made this way ( at least for know )
				// TODO find a better way to put a image here
				final String PATH = this.getClass().getResource("").getPath() + "../../resources/images/sasuke.png";
				this.view.setImage(new Image(new FileInputStream(new File(PATH))));
				hProfile.getChildren().addAll(this.view, vProfileNameEmail);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		view.setOnMouseClicked(e->{
			new ShowPicture(view.getImage(), Main.main_stage);
		});

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
}

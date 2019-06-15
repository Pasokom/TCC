package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.TimeZone;

import db.Database;
import db.functions.registrationAndLogin.HandlerLogin;
import db.pojo.UserSession;
import display.scenes.HomePage;
import display.scenes.Login;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static Stage main_stage;
	private final String IMAGE_PATH = this.getClass().getResource("").getPath() + "../../resources/images/Logo.png";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Database.get_connection();
		main_stage = primaryStage;

		Platform.setImplicitExit(false);

		main_stage.setWidth(875);
		main_stage.setHeight(515);
		//main_stage.setMaximized(true);
		main_stage.initStyle(StageStyle.DECORATED);
		main_stage.getIcons().add(new Image(new FileInputStream(new File(IMAGE_PATH))));

		TimeZone.getDefault();

		UserSession session = UserSession.fromSerialization();

		if(session.getUser_id() != 0){

			HandlerLogin login = new HandlerLogin();
			login.loginBySerialization(session.getUser_id());
			main_stage.setScene(new HomePage());
			main_stage.show();
			return;
		}

		main_stage.setScene(new Login());
		main_stage.show(); 
	}
}
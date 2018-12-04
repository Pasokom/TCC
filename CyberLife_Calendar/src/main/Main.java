package main;

import java.io.File;
import java.io.FileInputStream;

import db.Database;
import db.functions.registrationAndLogin.HandlerLogin;
import display.scenes.HomePage;
import display.scenes.Login;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import listeners.Serial;

public class Main extends Application {

	public static Stage main_stage;
	private Serial serial;
	private final String IMAGE_PATH = this.getClass().getResource("").getPath() + "../../resources/images/Logo.png";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.get_connection();
		main_stage = primaryStage;
		main_stage.setWidth(800);
		main_stage.setHeight(500);
		main_stage.getIcons().add(new Image(new FileInputStream(new File(IMAGE_PATH))));

		/**
		 * if the execution gets here, it means that the user has set the program to
		 * 'stay connected' then will have no need to open the login scene the home page
		 * scene will be open and the SESSION started
		 */
		this.serial = new Serial();
		if (serial.fileExists("stay_connected")) {
			int userID = (int) serial.undoSerialization("stay_connected");
			HandlerLogin login = new HandlerLogin();
			login.loginBySerialization(userID);
			main_stage.setScene(new HomePage());
			main_stage.show();
			return;
		}


		main_stage.setScene(new Login());
		main_stage.show();
	}
}
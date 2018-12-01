package main;

import db.Database;
import display.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage main_stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.get_connection();

		main_stage = primaryStage;

		main_stage.setScene(new Login());
		main_stage.setWidth(900);
		main_stage.setHeight(550);
		main_stage.setMinWidth(900);
		main_stage.setMinHeight(550);
		main_stage.show();
	}
}
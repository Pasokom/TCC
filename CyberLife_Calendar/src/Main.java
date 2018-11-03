
import db.Database;
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
		
		
		// db.queryTeste(); // fazendo testes

		main_stage = primaryStage;

		main_stage.setScene(new Reminder());
		
		main_stage.show();
//		janela = primaryStage;
//
//		main_stage.setScene(new HomePage());
//		
//		main_stage.show();		

	}
}

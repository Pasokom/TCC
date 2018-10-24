import db.Database;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database db = new Database(); // carrega o banco de dados 
		db.queryTeste(); // fazendo testes
		
		new Reminder();
	}
}

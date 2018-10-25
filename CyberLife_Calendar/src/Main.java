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
<<<<<<< HEAD
		//db.queryTeste(); // fazendo testes
		
		new Reminder(db);
=======
		db.queryTeste(); // fazendo testes
		
		new Reminder();
>>>>>>> d4a31e007f83403928bad47df25c53de27ed6387
	}
}

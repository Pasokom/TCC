package display;
import java.io.FileNotFoundException;

import component.ListCalendar;
import component.NavigationMenu;
import component.ProfileComponent;
import component.reminder.ListReminders;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Main;

public class HomePage extends Scene {

	private NavigationMenu menu;
	
	private ListCalendar reminderList;
	
	private ProfileComponent profileContent;
	
	public HomePage() throws FileNotFoundException {
		super (new VBox());
		
		Main.main_stage.setWidth(800);
		Main.main_stage.setHeight(500);

		menu = new NavigationMenu();
		AnchorPane.setLeftAnchor(menu, 0d);
		AnchorPane.setTopAnchor(menu, 0d);
		AnchorPane.setBottomAnchor(menu, 0d);
		
		reminderList = new ListCalendar();
		AnchorPane.setLeftAnchor(reminderList, menu.getPrefWidth());
		AnchorPane.setTopAnchor(reminderList, 0d);
		AnchorPane.setBottomAnchor(reminderList, 0d);
		
		AnchorPane layout = new  AnchorPane();
		
		layout.getChildren().addAll(menu, reminderList);

		this.setRoot(layout);
	}
}

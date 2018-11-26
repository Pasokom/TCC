package display;
import java.io.FileNotFoundException;
import java.util.Date;

import component.ListCalendar;
import component.NavigationMenu;
import component.ProfileComponent;
import component.reminder.ListReminders;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
		
		reminderList = new ListCalendar(new Date());
		AnchorPane.setLeftAnchor(reminderList, menu.getPrefWidth());
		AnchorPane.setTopAnchor(reminderList, 0d);
		AnchorPane.setBottomAnchor(reminderList, 0d);
		
		HBox calendar = new HBox();
		
		TextField dia = new TextField();
		Button buttonDia = new Button("Enviar");
		
		calendar.getChildren().addAll(dia, buttonDia);
		
		AnchorPane.setLeftAnchor(calendar,menu.getPrefWidth() + reminderList.getPrefWidth());
		AnchorPane.setRightAnchor(calendar, 0d);
		AnchorPane.setTopAnchor(calendar, 0d);
		AnchorPane.setBottomAnchor(calendar, 0d);
		
		AnchorPane layout = new  AnchorPane();
		
		layout.getChildren().addAll(menu, reminderList, calendar);

		buttonDia.setOnAction(e ->{
			
			reminderList = new ListCalendar(new Date(dia.getText()));
			AnchorPane.setLeftAnchor(reminderList, menu.getPrefWidth());
			AnchorPane.setTopAnchor(reminderList, 0d);
			AnchorPane.setBottomAnchor(reminderList, 0d);
			
			layout.getChildren().set(1, reminderList);
		});
		
		this.setRoot(layout);
	}
}

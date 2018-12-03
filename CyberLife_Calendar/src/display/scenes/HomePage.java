package display.scenes;

import java.io.FileNotFoundException;
import java.util.Calendar;

import component.CalendarBar;
import component.CalendarComponent;
import component.ListCalendar;
import component.NavigationMenu;
import component.ProfileComponent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Main;

public class HomePage extends Scene {

	private NavigationMenu menu;
	public static ListCalendar reminderList;
	private ProfileComponent profileContent;
	private AnchorPane layout;

	public HomePage() throws FileNotFoundException {
		super(new VBox());

		Main.main_stage.setWidth(800);
		Main.main_stage.setHeight(500);

		/* Configurando menu de navega��o */
		menu = new NavigationMenu();
		AnchorPane.setLeftAnchor(menu, 0d);
		AnchorPane.setTopAnchor(menu, 0d);
		AnchorPane.setBottomAnchor(menu, 0d);

		/* Configurando lista de itens do calendario */
		reminderList = new ListCalendar(Calendar.getInstance());
		AnchorPane.setLeftAnchor(reminderList, menu.getPrefWidth());
		AnchorPane.setTopAnchor(reminderList, 0d);
		AnchorPane.setBottomAnchor(reminderList, 0d);

		/* Configurando barra de sele��o do calendario */
		CalendarBar calendarBar = new CalendarBar();
		AnchorPane.setLeftAnchor(calendarBar, menu.getPrefWidth() + reminderList.getPrefWidth());
		AnchorPane.setRightAnchor(calendarBar, 0d);
		AnchorPane.setTopAnchor(calendarBar, 0d);

		CalendarComponent calendar = new CalendarComponent();

		// TextField dia = new TextField();
		// Button buttonDia = new Button("Enviar");
		//
		// calendar.getChildren().addAll(dia, buttonDia);

		AnchorPane.setLeftAnchor(calendar, menu.getPrefWidth() + reminderList.getPrefWidth() + 20);
		AnchorPane.setRightAnchor(calendar, 0d);
		AnchorPane.setTopAnchor(calendar, 100d);
		AnchorPane.setBottomAnchor(calendar, 0d);

		layout = new AnchorPane();
		layout.getChildren().addAll(menu, reminderList, calendarBar, calendar);

		// buttonDia.setOnAction(e ->{
		//
		// reminderList = new ListCalendar(new Date(dia.getText()));
		// AnchorPane.setLeftAnchor(reminderList, menu.getPrefWidth());
		// AnchorPane.setTopAnchor(reminderList, 0d);
		// AnchorPane.setBottomAnchor(reminderList, 0d);
		//
		// layout.getChildren().set(1, reminderList);
		// });

		this.setRoot(layout);
	}
}

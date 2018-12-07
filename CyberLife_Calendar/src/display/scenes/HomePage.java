package display.scenes;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import component.homepage.CalendarBar;
import component.homepage.CalendarComponent;
import component.homepage.ListCalendar;
import component.homepage.NavigationMenu;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Main;
import statics.NotifyUser;

public class HomePage extends Scene {

	private NavigationMenu menu;
	public static ListCalendar listCalendar;
	public static CalendarComponent calendarComponent;
	private AnchorPane layout;

	public HomePage() throws FileNotFoundException {
		super(new VBox());

		NotifyUser.init();

		Main.main_stage.setWidth(800);
		Main.main_stage.setHeight(500);

		/* Configurando menu de navega��o */
		menu = new NavigationMenu();
		AnchorPane.setLeftAnchor(menu, 0d);
		AnchorPane.setTopAnchor(menu, 0d);
		AnchorPane.setBottomAnchor(menu, 0d);

		Calendar date = Calendar.getInstance();

		/* Configurando lista de itens do calendario */
		listCalendar = new ListCalendar(date);
		AnchorPane.setLeftAnchor(listCalendar, menu.getPrefWidth());
		AnchorPane.setTopAnchor(listCalendar, 0d);
		AnchorPane.setBottomAnchor(listCalendar, 0d);

		/* Configurando barra de sele��o do calendario */
		CalendarBar calendarBar = new CalendarBar(date);
		AnchorPane.setLeftAnchor(calendarBar, menu.getPrefWidth() + listCalendar.getPrefWidth());
		AnchorPane.setRightAnchor(calendarBar, 0d);
		AnchorPane.setTopAnchor(calendarBar, 0d);

		calendarComponent = new CalendarComponent(date);

		AnchorPane.setLeftAnchor(calendarComponent, menu.getPrefWidth() + listCalendar.getPrefWidth() + 20);
		AnchorPane.setRightAnchor(calendarComponent, 0d);
		AnchorPane.setTopAnchor(calendarComponent, 100d);
		AnchorPane.setBottomAnchor(calendarComponent, 0d);

		layout = new AnchorPane();
		layout.getChildren().addAll(menu, listCalendar, calendarBar, calendarComponent);

		this.setRoot(layout);
	}
}
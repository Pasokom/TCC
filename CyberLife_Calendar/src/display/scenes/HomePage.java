package display.scenes;

import java.util.Calendar;
import component.homepage.CalendarBar;
import component.homepage.CalendarComponent;
import component.homepage.Goals;
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
	public static Goals goals;
	private AnchorPane layout;

	public HomePage() {
		super(new VBox());

		NotifyUser.init();

		/* Configurando menu de navegação */
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

		/* Configurando barra de seleção do calendario */
		CalendarBar calendarBar = new CalendarBar(date);
		AnchorPane.setLeftAnchor(calendarBar, menu.getPrefWidth() + listCalendar.getPrefWidth());
		AnchorPane.setRightAnchor(calendarBar, 0d);
		AnchorPane.setTopAnchor(calendarBar, 0d);

		calendarComponent = new CalendarComponent(date);

		AnchorPane.setLeftAnchor(calendarComponent, menu.getPrefWidth() + listCalendar.getPrefWidth());
		AnchorPane.setRightAnchor(calendarComponent, 0d);
		AnchorPane.setTopAnchor(calendarComponent, 85d);
		AnchorPane.setBottomAnchor(calendarComponent, 0d);

		/* Configurando visualizador de metas */
		goals = new Goals();
		AnchorPane.setTopAnchor(goals, 0d);
		AnchorPane.setRightAnchor(goals, 0d);
		AnchorPane.setBottomAnchor(goals, 0d);
		AnchorPane.setLeftAnchor(goals, menu.getPrefWidth());
		goals.setManaged(false);
		goals.setVisible(false);

		layout = new AnchorPane();
		layout.getChildren().addAll(menu, listCalendar, calendarBar, calendarComponent, goals);

		this.setRoot(layout);
	}
}
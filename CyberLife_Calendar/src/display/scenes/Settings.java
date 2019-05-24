package display.scenes;

import java.util.Calendar;

import component.TimePicker;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Settings extends Scene {

    private TabPane tabs;
    private Tab tab_general;
    private Tab tab_user;
    private Tab tab_notifications;

    public Settings() {
        super(new VBox());

        tab_general = general();
        tab_user = user();
        tab_notifications = notifications();

        tabs = new TabPane();
        tabs.getTabs().addAll(tab_general, tab_user, tab_notifications);

        this.setRoot(tabs);
    }

    private Tab general() {

        Label lbl_task_schedule = new Label("Horario de tarefas");
        
        TimePicker t_begin = new TimePicker(Calendar.getInstance());
        TimePicker t_end = new TimePicker(Calendar.getInstance());

        HBox hb_task_schedule = new HBox();
        hb_task_schedule.getChildren().addAll(t_begin, t_end);

        VBox vb_content = new VBox();
        vb_content.getChildren().addAll(lbl_task_schedule, hb_task_schedule);

        Tab tab = new Tab("Geral");
        tab.setClosable(false);
        tab.setContent(vb_content);

        return tab;
    }

    private Tab user() {

        Tab tab = new Tab("Usuário");
        tab.setClosable(false);

        HBox hb_user = new HBox();

        return tab;
    }

    private Tab notifications() {

        Tab tab = new Tab("Notificações");
        tab.setClosable(false);

        return tab;
    }
}
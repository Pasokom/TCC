package display.poupoup;

import java.util.ArrayList;

import component.NotificationComponent;
import db.functions.user.LoadNotifications;
import db.pojo.NotificationDB;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

public class Notifications extends Popup {

    private VBox vb_notifications;
    private ArrayList<NotificationDB> notifications;

    public Notifications() {
        
        vb_notifications = new VBox();
        vb_notifications.setMinSize(300, 300);
        vb_notifications.setMaxSize(300, 300);
        vb_notifications.getStyleClass().add("this");
        vb_notifications.getStylesheets().add(this.getClass().getResource("../../css/notifications.css").toExternalForm());

        LoadNotifications loader = new LoadNotifications();
        notifications = loader.load();

        for (NotificationDB notification : notifications) {
            
            vb_notifications.getChildren().addAll(new NotificationComponent(notification), new Separator());
        }

        DropShadow shadow = new DropShadow();
        vb_notifications.setEffect(shadow);
        
        this.getContent().add(vb_notifications);
        this.setAutoHide(true);
    }    
}
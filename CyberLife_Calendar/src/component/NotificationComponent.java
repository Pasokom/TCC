package component;

import db.functions.user.ManageNotifications;
import db.pojo.NotificationDB;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NotificationComponent extends HBox {

    private Label text;
    private Button btn_accept;
    private Button btn_dismiss;

    public NotificationComponent(NotificationDB notification) {
        
        text = new Label(notification.getTitle());
        text.setWrapText(true);

        btn_accept = new Button("Aceitar");
        btn_accept.setPrefWidth(160);

        btn_accept.setOnAction(e -> {

            ManageNotifications manage = new ManageNotifications();
            manage.accept(notification);
        });

        btn_dismiss = new Button("Recusar");
        btn_dismiss.setPrefWidth(160);
        
        btn_dismiss.setOnAction(e -> {

            ManageNotifications manage = new ManageNotifications();
            manage.dismiss(notification);
        });

        this.setPadding(new Insets(10));
        this.setSpacing(5);
        this.getChildren().addAll(text, btn_accept, btn_dismiss);
    }
}
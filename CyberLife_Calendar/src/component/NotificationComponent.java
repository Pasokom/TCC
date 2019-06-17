package component;

import db.functions.appointment.LoadAppointment;
import db.functions.registrationAndLogin.HandlerLogin;
import db.functions.user.ManageNotifications;
import db.pojo.NotificationDB;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import statics.SESSION;

public class NotificationComponent extends HBox {

    private Label text;
    private Button btn_accept;
    private Button btn_dismiss;

    private Circle profileImg;
    private StackPane userImg;
    private Label userInitial;
    private Image img;

    public NotificationComponent(NotificationDB notification) {
        
        text = new Label(notification.getTitle());
        text.setWrapText(true);

        this.getStylesheets().add(this.getClass().getResource("../css/notificationComponent.css").toExternalForm());

        profileImg = new Circle();
        profileImg.setRadius(20);
		profileImg.setCenterX(100);
        profileImg.setCenterY(100);
        
        userImg = new StackPane();

        HandlerLogin handlerLogin = new HandlerLogin();
        LoadAppointment load = new LoadAppointment();

        int cod_usuario = load.loadProject(notification.getProject_cod()).getFk_usuario();

		if (handlerLogin.userImageExists(cod_usuario)){

			img = new Image("http://localhost/cyberlife/imagens/img" + cod_usuario + ".jpeg");
			profileImg.setFill(new ImagePattern(img));
			userImg.getChildren().addAll(profileImg);
		}
		else {

			Image img = new Image("http://localhost/cyberlife/imagens/person.png");
			profileImg.setFill(new ImagePattern(img));
			userImg.getChildren().addAll(profileImg);
		}

        btn_accept = new Button();
        btn_accept.setId("btnDone");    

        btn_accept.setOnAction(e -> {

            this.getScene().getWindow().hide();
            ManageNotifications manage = new ManageNotifications();
            manage.accept(notification);

            
        });

        btn_dismiss = new Button();
        btn_dismiss.setId("btnCancel");

        btn_dismiss.setOnAction(e -> {

            this.getScene().getWindow().hide();
            ManageNotifications manage = new ManageNotifications();
            manage.dismiss(notification);
        });

        this.setPadding(new Insets(15));
        this.setSpacing(5);
        this.getChildren().addAll(userImg, text, btn_accept, btn_dismiss);
    }
}
package component.project;

import db.functions.registrationAndLogin.HandlerLogin;
import db.pojo.FeedTaskDB;
import db.pojo.projectPOJO.TarefaDB;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class FeedComponent extends HBox {

    private Label lbl_message;
    private Label lbl_time;
    private Circle profileImg;

    public FeedComponent(FeedTaskDB task) {
        
        this.getStylesheets().add(this.getClass().getResource("../../css/feed_component.css").toExternalForm());

        profileImg = new Circle();
        profileImg.setRadius(20);
		profileImg.setCenterX(100);
        profileImg.setCenterY(100);
        
        StackPane userImg = new StackPane();
        Image img;
        Label userInitial;
        HandlerLogin handlerLogin = new HandlerLogin();

		if (handlerLogin.userImageExists(task.getFk_usuario())){

			img = new Image("http://localhost/cyberlife/imagens/img" + task.getFk_usuario() + ".jpeg");
			profileImg.setFill(new ImagePattern(img));
			userImg.getChildren().addAll(profileImg);
		}
		else {

			userInitial = new Label(task.getUsuario_nome().substring(0, 1).toUpperCase());
			userInitial.setFont(new Font(15));
			userImg.getChildren().addAll(profileImg, userInitial);
        }
        
        lbl_message = new Label(task.getUsuario_nome() + " concluiu " + task.getTarefa_nome());
        lbl_time = new Label("28/11/2019 15:30");

        VBox vb_message_time = new VBox(lbl_message, lbl_time);

        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);

        this.setEffect(shadow);
        this.setSpacing(10);
        this.getChildren().addAll(profileImg, vb_message_time);
        this.getStyleClass().add("this");
    }    
}
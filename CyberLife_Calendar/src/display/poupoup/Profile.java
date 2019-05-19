package display.poupoup;

import component.Instructions.Intro_Foda;
import component.homepage.NavigationMenu;
import db.pojo.UserSession;
import display.scenes.Login;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.Main;
import statics.SESSION;

/**
 * Profile
 */
public class Profile extends Popup {

    Label lbl_name, lbl_email;

    ImageView img_exit; Label lbl_exit;
    ImageView img_config; Label lbl_config;
    ImageView img_help; Label lbl_help;

    public Profile() {

        EditProfile editProfile = new EditProfile();


        VBox root = new VBox();
        root.getStylesheets().add(this.getClass().getResource("../../css/profile.css").toExternalForm());

        HBox hb_profile = new HBox();

        Circle profileImg = new Circle();

        Image img = new Image("http://localhost/cyberlife/imagens/a.jpeg");
        profileImg.setFill(new ImagePattern(img));
        profileImg.setRadius(20);
		profileImg.setCenterX(100);
		profileImg.setCenterY(100);

		StackPane userImg = new StackPane();
/* 		Label userInitial = new Label(SESSION.get_user_name().substring(0, 1).toUpperCase());
		userInitial.setFont(new Font(20)); */
        userImg.getChildren().addAll(profileImg);
        userImg.setId("img_profile");

        VBox vb_email_name = new VBox();
        lbl_name = new Label(SESSION.get_user_name() + " " + SESSION.get_user_last_name());
        lbl_name.setId("name");
        lbl_email = new Label(SESSION.get_user_email());

        vb_email_name.getChildren().addAll(lbl_name, lbl_email);
        hb_profile.getChildren().addAll(userImg, vb_email_name);

        VBox vb_options = new VBox();

        HBox hb_exit = new HBox();
        img_exit = new ImageView();
        img_exit.setId("img_exit");
        lbl_exit = new Label("Sair");
        hb_exit.getChildren().addAll(img_exit, lbl_exit);
        hb_exit.getStyleClass().add("option");

        Separator separator = new Separator();

        lbl_exit.setOnMouseClicked(e -> {

            UserSession.close();

			SESSION.END_SESSION();
			Main.main_stage.setScene(new Login());
		});

        hb_exit.setOnMouseClicked(e -> {

        	UserSession.close();

			SESSION.END_SESSION();
			Main.main_stage.setScene(new Login());
			this.hide();
        });

        HBox hb_settings = new HBox();
        img_config = new ImageView();
        img_config.setId("img_config");
        lbl_config = new Label("Configurações");
        lbl_config.setAlignment(Pos.CENTER_RIGHT);
        hb_settings.getChildren().addAll(img_config, lbl_config);
        hb_settings.getStyleClass().add("option");

        lbl_config.setOnMouseClicked(e -> {
			editProfile.show();
		});

        HBox hb_help = new HBox();
        img_help = new ImageView();
        img_help.setId("img_help");
        lbl_help = new Label("Ajuda");
        hb_help.getChildren().addAll(img_help, lbl_help);
        hb_help.getStyleClass().add("option");

        hb_help.setOnMouseClicked(e ->{
            this.hide();
            Intro_Foda intro = new Intro_Foda();
            intro.show(this.getOwnerWindow());
        });

        vb_options.getChildren().addAll(hb_exit, separator, hb_settings, hb_help);
        root.getChildren().addAll(hb_profile, vb_options);
        DropShadow shadow = new DropShadow();
        root.setEffect(shadow);
        root.setMinWidth(250);

        this.getContent().add(root);
        this.setAutoHide(true);
    }
}
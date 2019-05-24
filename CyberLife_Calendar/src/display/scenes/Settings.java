package display.scenes;

import java.io.File;
import java.util.Calendar;

import component.TimePicker;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import statics.SESSION;

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

        this.getStylesheets().add(this.getClass().getResource("../../css/settings.css").toExternalForm());

        Circle profileImg = new Circle();

        profileImg.setRadius(50);
		profileImg.setCenterX(100);
        profileImg.setCenterY(100);

        Circle profileEdit = new Circle();
        profileEdit.setRadius(50);
		profileEdit.setCenterX(100);
        profileEdit.setCenterY(100);
        
        StackPane userImg = new StackPane();

        Image img = new Image("http://localhost/cyberlife/imagens/img" + SESSION.get_user_cod() + ".jpeg");
        Image imgPhoto = new Image("http://localhost/cyberlife/imagens/camera.png");
        
        profileImg.setFill(new ImagePattern(img));
        profileEdit.setFill(new ImagePattern(imgPhoto));
        userImg.getChildren().addAll(profileEdit, profileImg);

        profileImg.setOnMouseClicked(y ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"),
            new ExtensionFilter("All Files", "*.*"));

            fileChooser.showOpenDialog(new Stage());
        });

        profileImg.setOnMouseEntered(e ->{
            profileImg.setOpacity(0.5);
        });

        profileImg.setOnMouseExited(e ->{
            profileImg.setOpacity(5);
        });
        
        Button btn_cancell = new Button("Cancelar");
        Button btn_save = new Button("Salvar");

        Label lbl_name = new Label("Nome");
        TextField txt_name = new TextField(SESSION.get_user_name());
        TextField txt_lastName = new TextField(SESSION.get_user_last_name());

        Label lbl_email = new Label("Email");
        TextField txt_email = new TextField(SESSION.get_user_email());

        Label lbl_passCurrent = new Label("Senha Atual");
        TextField txt_passCurrent = new TextField();

        Label lbl_newPass = new Label("Nova senha");
        TextField txt_newPass = new TextField();

        Tab tab = new Tab("Usuário");
        tab.setClosable(false);

        HBox hb_button = new HBox();
        hb_button.getChildren().addAll(btn_cancell, btn_save);
        hb_button.setId("button");

        HBox hb_name = new HBox();
        hb_name.getChildren().addAll(txt_name, txt_lastName);

        hb_name.setSpacing(5);
        hb_button.setSpacing(5);

        VBox vb_content = new VBox();
        vb_content.getChildren().addAll(userImg, hb_name,
            lbl_email, txt_email, lbl_passCurrent, txt_passCurrent, lbl_newPass, txt_newPass, hb_button);

        vb_content.setSpacing(7);
        vb_content.getStyleClass().add("usuario");

        tab.setContent(vb_content);

        return tab;
    }

    private Tab notifications() {

        Tab tab = new Tab("Notificações");
        tab.setClosable(false);

        return tab;
    }
}
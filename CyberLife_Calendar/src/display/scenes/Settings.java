package display.scenes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Time;
import java.util.Calendar;

import component.TimePicker;
import db.functions.registrationAndLogin.HandlerLogin;
import db.functions.user.AccountSettings;
import db.pojo.UserDB;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import statics.SESSION;

public class Settings extends Scene {

    private TabPane tabs;
    private Tab tab_general;
    private Tab tab_user;
    private Button save;

    private TimePicker t_begin;
    private TimePicker t_end;

    private Label userInitial;
    private Image img;

    public Settings() {
        super(new VBox());

        tab_general = general();
        tab_user = user();

        tabs = new TabPane();
        tabs.getTabs().addAll(tab_general, tab_user);

        this.setRoot(tabs);
        tabs.requestFocus();
    }

    private Tab general() {

        AccountSettings accountSettings = new AccountSettings();

        Label lbl_task_schedule = new Label("Horario de tarefas");

        t_begin = new TimePicker(accountSettings.startTask());
        t_end = new TimePicker(accountSettings.endTask());

        Label lbl_until = new Label("até");

        save = new Button("salvar");

        save.setOnAction(e ->{

            if(Time.valueOf(t_begin.get_value() + ":00").getTime() < Time.valueOf(t_end.get_value() + ":00").getTime()) {

                accountSettings.saveScheduleTask(Time.valueOf(t_begin.get_value() + ":00"), Time.valueOf(t_end.get_value() + ":00"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText("Sucesso!!!");
				alert.setContentText("O horário de tarefas foi alterado com sucesso!");
				alert.showAndWait();
            }
            else {

                Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setHeaderText("Erro");
				alert.setContentText("A hora inicial deve ser antes da hora final!");
				alert.showAndWait();
            }

        });

        HBox hb_task_schedule = new HBox();
        hb_task_schedule.setSpacing(5);
        hb_task_schedule.getChildren().addAll(t_begin, lbl_until, t_end);

        VBox vb_content = new VBox();
        vb_content.setSpacing(10);
        vb_content.setPadding(new Insets(10));
        vb_content.getChildren().addAll(lbl_task_schedule, hb_task_schedule, save);

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

        HandlerLogin handlerLogin = new HandlerLogin();
        
        userInitial = new Label();

        StackPane userImg = new StackPane();
        if (handlerLogin.userImageExists((int)SESSION.get_user_cod())){

            img = new Image("http://localhost/cyberlife/imagens/img" + SESSION.get_user_cod() + ".jpeg");
			profileImg.setFill(new ImagePattern(img));
		}
		else {

            Image img = new Image("http://localhost/cyberlife/imagens/person.png");
			profileImg.setFill(new ImagePattern(img));
        }
        
        Image imgPhoto = new Image("http://localhost/cyberlife/imagens/camera.png");
        profileEdit.setFill(new ImagePattern(imgPhoto));
        profileEdit.setOpacity(0);
        userImg.getChildren().addAll(profileImg, profileEdit);

        profileEdit.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Escolha uma imagem");
            fileChooser.getExtensionFilters().addAll(
            new ExtensionFilter("Arquivos de imagem", "*.png", "*.jpg", "*.gif", "*.jpeg"),
            new ExtensionFilter("Todos os arquivos", "*.*"));
            
            File selectedFile;

            File dest = new File("C:\\Apache24\\htdocs\\cyberlife\\imagens\\img" + SESSION.get_user_cod() + ".jpeg");

            selectedFile = fileChooser.showOpenDialog(new Stage());

            if(selectedFile != null){
               
                if(dest.exists()){
                    dest.delete();
                }

                try {
                    copyFileUsingJava7Files(selectedFile, dest);
                    Image imgUp = new Image("http://localhost/cyberlife/imagens/img" + SESSION.get_user_cod() + ".jpeg");
                    profileImg.setFill(new ImagePattern(imgUp));
                    //userInitial.setText("");
                    HomePage.menu.update();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        profileEdit.setOnMouseEntered(e ->{
            profileEdit.setOpacity(0.3);
        });

        profileEdit.setOnMouseExited(e ->{
            profileEdit.setOpacity(0);
        });
        
        Button btn_cancell = new Button("Cancelar");
        Button btn_save = new Button("Salvar");

        Label lbl_name = new Label("Nome");
        TextField txt_name = new TextField(SESSION.get_user_name());
        TextField txt_lastName = new TextField(SESSION.get_user_last_name());

        Label lbl_email = new Label("Email");
        TextField txt_email = new TextField(SESSION.get_user_email());
        txt_email.setMaxWidth(200);

        Label lbl_passCurrent = new Label("Senha Atual");
        PasswordField pwd_pass = new PasswordField();

        VBox vb_current_password = new VBox(lbl_passCurrent, pwd_pass);
        vb_current_password.setSpacing(5);

        Label lbl_newPass = new Label("Nova senha");
        PasswordField txt_newPass = new PasswordField();

        VBox vb_new_password = new VBox(lbl_newPass, txt_newPass);
        vb_new_password.setSpacing(5);

        HBox hb_password = new HBox(vb_current_password, vb_new_password);
        hb_password.setSpacing(20);

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
            lbl_email, txt_email, hb_password, hb_button);

        vb_content.setSpacing(7);
        vb_content.getStyleClass().add("usuario");

        tab.setContent(vb_content);

        btn_save.setOnAction(e -> {

            AccountSettings settings = new AccountSettings();
            settings.changeProfile(txt_name.getText(), txt_lastName.getText(), txt_email.getText(), pwd_pass.getText(), txt_newPass.getText());
        });

        btn_cancell.setOnAction(e ->{
            ((Stage)this.getWindow()).close();
        });

        return tab;
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
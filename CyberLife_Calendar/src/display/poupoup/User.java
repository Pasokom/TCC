package display.poupoup;

import component.project.Team;
import db.functions.projectFeatures.CreateFeature;
import display.scenes.HomePage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import statics.SESSION;

public class User extends Popup {

    private TextField txt_email;
    private Button btn_send;

    private int cod_project;

    public User(int cod_project) {

        this.cod_project = cod_project;

        txt_email = new TextField();
        txt_email.setPromptText("Email");
        txt_email.setId("title");
        txt_email.setPrefWidth(300);
        btn_send = new Button("Enviar solicitação");
            
        btn_send.setOnAction(e -> {

            if(!txt_email.getText().toLowerCase().equals(SESSION.get_user_email().toLowerCase())){
                
                CreateFeature create = new CreateFeature();
                create.sendNotification(txt_email.getText(), this.cod_project);
                HomePage.project.setContentPane(new Team(this.cod_project));

                this.hide();
                
            }else{

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("ERRO");
                alert.setHeaderText("Convite inválido");
                alert.setContentText("Non pode convidar você mesmo");
                alert.showAndWait();
            }

        });
        
        VBox root = new VBox(txt_email, btn_send);
        root.getStyleClass().add("root");
        root.getStylesheets().add(this.getClass().getResource("../../css/add_user.css").toExternalForm());
        root.setPadding(new Insets(15));
        root.setSpacing(5);

        DropShadow shadow = new DropShadow();
        root.setEffect(shadow);
        
        this.getContent().add(root);
        this.setAutoHide(true);
    }
}
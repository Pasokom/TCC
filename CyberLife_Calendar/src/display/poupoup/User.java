package display.poupoup;

import db.functions.projectFeatures.CreateFeature;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

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
            
            CreateFeature create = new CreateFeature();
            create.sendNotification(txt_email.getText(), this.cod_project);
            this.hide();
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
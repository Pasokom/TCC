package component.project;

import display.poupoup.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Team extends VBox {

    private Button btn_add_user;
    private Label lbl_all_users;
    private FlowPane flw_all_users;    

    private int cod_project;

    public Team(int cod_project) {
        
        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/project_component.css").toExternalForm());

        btn_add_user = new Button(" + adicionar integrante");
        btn_add_user.setId("btn_add_user");
        lbl_all_users = new Label("Todos os integrantes");
        flw_all_users = new FlowPane();

        btn_add_user.setOnAction(e -> {

            User user = new User(this.cod_project);
            user.show(this.getScene().getWindow());   
        });

        this.setSpacing(10);
        this.getChildren().addAll(btn_add_user, lbl_all_users, flw_all_users);
    }    
}
package component.project;

import java.util.ArrayList;

import component.projectFeatures.FeatureComponent;
import db.functions.projectFeatures.LoadFeature;
import db.pojo.UserDB;
import display.poupoup.User;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Team extends VBox {

    private Button btn_add_user;
    private Label lbl_all_users;
    private FlowPane flw_all_users;
    private Label lbl_progress;
    private FlowPane flw_progress;    

    private int cod_project;

    public Team(int cod_project) {
        
        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/project_component.css").toExternalForm());

        btn_add_user = new Button(" + adicionar integrante");
        btn_add_user.setId("btn_add_user");
        lbl_all_users = new Label("Todos os integrantes");
        flw_all_users = new FlowPane();
        flw_all_users.setHgap(10);
        flw_all_users.setVgap(10);

        btn_add_user.setOnAction(e -> {

            User user = new User(this.cod_project);

            Point2D point2d = btn_add_user.localToScreen(0d, 0d);

			user.setX(point2d.getX() + btn_add_user.getWidth());
			user.setY(point2d.getY() - 5);

            user.show(this.getScene().getWindow());   
        });

        LoadFeature feature = new LoadFeature();
        ArrayList<UserDB> members = feature.loadMembers(this.cod_project);

        flw_progress = new FlowPane();
        flw_progress.setHgap(15);
        flw_progress.setVgap(10);

        lbl_progress = new Label("Progresso");

        for (UserDB member : members) {
            
            FeatureComponent member_component = new FeatureComponent(member, cod_project);
            member_component.setMaxWidth(200);
            flw_all_users.getChildren().addAll(member_component);
            flw_progress.getChildren().addAll(new MemberProgress(member, cod_project));
        }

        this.setSpacing(10);
        this.getChildren().addAll(btn_add_user, lbl_all_users, flw_all_users, lbl_progress, flw_progress);
    }    
}
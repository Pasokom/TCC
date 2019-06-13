package component.project;

import java.util.ArrayList;

import component.CustomScroll;
import component.project.performance.ProjectMember;
import component.project.performance.ProjectTeam;
import db.functions.projectFeatures.LoadFeature;
import db.pojo.UserDB;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Performance extends CustomScroll {

    private ProjectTeam projectTeam;
    private Label lbl_members;
    private FlowPane flw_members; 

    private int cod_project;

    public Performance(int cod_project) {
        
        this.cod_project = cod_project;

        this.getStylesheets().add(this.getClass().getResource("../../css/project_component.css").toExternalForm());

        projectTeam = new ProjectTeam(this.cod_project);

        LoadFeature feature = new LoadFeature();
        ArrayList<UserDB> members = feature.loadMembers(this.cod_project);

        flw_members = new FlowPane();
        flw_members.setHgap(15);
        flw_members.setVgap(10);

        lbl_members = new Label("Equipe");

        for (UserDB member : members) {
            
            ProjectMember projectMember = new ProjectMember(member, this.cod_project);
            flw_members.getChildren().add(projectMember);
        }

        VBox vb_content = new VBox(projectTeam, lbl_members, flw_members);
        vb_content.setSpacing(10);
        vb_content.setPadding(new Insets(10));
        this.setContent(vb_content);
        this.getStyleClass().add("custom-scroll");
    }
}
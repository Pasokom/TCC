package component.homepage;

import db.pojo.projectPOJO.ProjectDB;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Project extends VBox{

    private Label lbl_title;
    private Label lbl_begin;
    private Label lbl_date_begin;
    private Label lbl_final;
    private Label lbl_date_final;

    public Project() {
        
        this.getStylesheets().add(this.getClass().getResource("../../css/goals.css").toExternalForm());
        this.getStyleClass().add("this");

        lbl_title = new Label();

        this.getChildren().add(lbl_title);
    }

    public void loadProject(ProjectDB project) {

        lbl_title.setText(project.getTitulo());
    }
}
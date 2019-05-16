package component.homepage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import component.project.Tasks;
import db.pojo.projectPOJO.ProjectDB;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Project extends VBox{

    private Label lbl_title;
    private Label lbl_begin;
    private Label lbl_date_begin;
    private Label lbl_final;
    private Label lbl_date_final;

    private StackPane content_pane;

    public Project() {
        
        this.getStylesheets().add(this.getClass().getResource("../../css/project_homepage.css").toExternalForm());
        this.getStyleClass().add("this");
        
        DropShadow shadow = new DropShadow();
		shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        
        lbl_title = new Label();
        lbl_title.setId("title");
        //lbl_title.setMinWidth(300);

        //lbl_title.setEffect(shadow);

        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(new ToggleButton("Feed"));
        toolbar.getItems().add(new ToggleButton("Tarefas"));
        toolbar.getItems().add(new ToggleButton("Desempenho"));
        toolbar.getItems().add(new ToggleButton("Equipe"));
        toolbar.getItems().add(new ToggleButton("Marcadores"));
        toolbar.getItems().add(new ToggleButton("Configurações"));

        StackPane pane = new StackPane();
        pane.getChildren().add(toolbar);
        pane.setAlignment(Pos.CENTER_LEFT);

        FlowPane header = new FlowPane();
        header.setHgap(20);
        header.setVgap(20);
        header.getChildren().addAll(lbl_title, pane);

        content_pane = new StackPane();
        this.setSpacing(20);
        this.getChildren().addAll(header, content_pane);
    }

    public void loadProject(ProjectDB project) {

        lbl_title.setText(project.getTitulo());

        if (content_pane.getChildren().size() == 0)
            content_pane.getChildren().add(0, new Tasks(project.getCod_projeto()));
        else
            content_pane.getChildren().set(0, new Tasks(project.getCod_projeto()));
    }

    private String date(Timestamp date) {

        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd-MM-yyyy");
        return datetimeFormatter1.format(date); 
    }
}
package component.homepage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import component.project.Labels;
import component.project.Tasks;
import db.pojo.projectPOJO.ProjectDB;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
    private ToolBar toolbar;

    private StackPane content_pane;

    private int cod_project;

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

        toolbar = new ToolBar();

        ToggleGroup grp_toolbar = new ToggleGroup();

        ToggleButton btn_feed = new ToggleButton("Feed");
        ToggleButton btn_tasks = new ToggleButton("Tarefas");
        ToggleButton btn_performance = new ToggleButton("Desempenho");
        ToggleButton btn_team = new ToggleButton("Equipe");
        ToggleButton btn_labels = new ToggleButton("Marcadores");
        ToggleButton btn_settings = new ToggleButton("Configurações");
        
        btn_feed.setToggleGroup(grp_toolbar);
        btn_tasks.setToggleGroup(grp_toolbar);
        btn_performance.setToggleGroup(grp_toolbar);
        btn_team.setToggleGroup(grp_toolbar);
        btn_labels.setToggleGroup(grp_toolbar);
        btn_settings.setToggleGroup(grp_toolbar);
        
        toolbar.getItems().add(btn_feed);
        toolbar.getItems().add(btn_tasks);
        toolbar.getItems().add(btn_performance);
        toolbar.getItems().add(btn_team);
        toolbar.getItems().add(btn_labels);
        toolbar.getItems().add(btn_settings);

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

        btn_tasks.setOnAction(e -> {
            content_pane.getChildren().set(0, new Tasks(cod_project));
        });

        btn_labels.setOnAction(e -> {
            content_pane.getChildren().set(0, new Labels(cod_project));
        });
    }

    public void loadProject(ProjectDB project) {
        this.cod_project = project.getCod_projeto();

        lbl_title.setText(project.getTitulo());

        if (content_pane.getChildren().size() == 0)
            content_pane.getChildren().add(0, new Tasks(project.getCod_projeto()));
        else
            content_pane.getChildren().set(0, new Tasks(project.getCod_projeto()));

        ((ToggleButton) toolbar.getItems().get(1)).setSelected(true);
    }

    private String date(Timestamp date) {

        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd-MM-yyyy");
        return datetimeFormatter1.format(date); 
    }
}
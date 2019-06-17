package component.homepage;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import component.CustomScroll;
import component.project.Feed;
import component.project.Labels;
import component.project.Performance;
import component.project.Settings;
import component.project.Tasks;
import component.project.Team;
import db.pojo.projectPOJO.ProjectDB;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Project extends CustomScroll {

    private Label lbl_title;
    private Label lbl_begin;
    private Label lbl_date_begin;
    private Label lbl_final;
    private Label lbl_date_final;
    private ToolBar toolbar;

    private StackPane content_pane;

    private int cod_project;

    public Project() {

        VBox vb_content = new VBox();

        vb_content.getStylesheets().add(this.getClass().getResource("../../css/project_homepage.css").toExternalForm());
        vb_content.getStyleClass().add("this");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);

        lbl_title = new Label();
        lbl_title.setId("title");

        toolbar = new ToolBar();
        toolbar.getStyleClass().add("toolbar");

        Tooltip tooltip = new Tooltip();

        ToggleGroup grp_toolbar = new ToggleGroup();

        ToggleButton btn_feed = new ToggleButton();
        btn_feed.setId("btnFeed");
        btn_feed.getStyleClass().add("toggle-button");
        ToggleButton btn_tasks = new ToggleButton();
        btn_tasks.setId("btnTasks");
        ToggleButton btn_performance = new ToggleButton();
        btn_performance.setId("btnPerformance");
        ToggleButton btn_team = new ToggleButton();
        btn_team.setId("btnTeam");
        ToggleButton btn_labels = new ToggleButton();
        btn_labels.setId("btnLabels");
        ToggleButton btn_settings = new ToggleButton();
        btn_settings.setId("btnSettings");

        btn_feed.setToggleGroup(grp_toolbar);
        btn_tasks.setToggleGroup(grp_toolbar);
        btn_performance.setToggleGroup(grp_toolbar);
        btn_team.setToggleGroup(grp_toolbar);
        btn_labels.setToggleGroup(grp_toolbar);
        btn_settings.setToggleGroup(grp_toolbar);

        grp_toolbar.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
			if (newVal == null)
				oldVal.setSelected(true);
		});

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
        vb_content.setSpacing(20);
        vb_content.getChildren().addAll(header, content_pane);

        btn_feed.setOnMouseEntered(e -> {

            Point2D point = btn_feed.localToScreen(0d, 0d);

            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Feed");
            tooltip.show(this.getScene().getWindow());
        });

        btn_feed.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_tasks.setOnMouseEntered(e -> {
            Point2D point = btn_tasks.localToScreen(0d, 0d);
            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Tarefas");
            tooltip.show(this.getScene().getWindow());
        });

        btn_tasks.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_performance.setOnMouseEntered(e -> {
            Point2D point = btn_performance.localToScreen(0d, 0d);
            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Performance");
            tooltip.show(this.getScene().getWindow());
        });

        btn_performance.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_team.setOnMouseEntered(e -> {
            Point2D point = btn_team.localToScreen(0d, 0d);
            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Equipe");
            tooltip.show(this.getScene().getWindow());
        });

        btn_team.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_labels.setOnMouseEntered(e -> {
            Point2D point = btn_labels.localToScreen(0d, 0d);
            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Marcadores");
            tooltip.show(this.getScene().getWindow());
        });

        btn_labels.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_settings.setOnMouseEntered(e -> {
            Point2D point = btn_settings.localToScreen(0d, 0d);
            tooltip.setX(point.getX());
            tooltip.setY(point.getY() + btn_feed.getHeight());
            tooltip.setText("Configurações");
            tooltip.show(this.getScene().getWindow());
        });

        btn_settings.setOnMouseExited(e -> {
            tooltip.hide();
        });

        btn_feed.setOnAction(e -> {
            content_pane.getChildren().set(0, new Feed(cod_project));
        });

        btn_tasks.setOnAction(e -> {
            content_pane.getChildren().set(0, new Tasks(cod_project));
        });

        btn_performance.setOnAction(e -> {
            content_pane.getChildren().set(0, new Performance(cod_project));
        });

        btn_team.setOnAction(e -> {
            content_pane.getChildren().set(0, new Team(cod_project));
        });

        btn_labels.setOnAction(e -> {
            content_pane.getChildren().set(0, new Labels(cod_project));
        });

        btn_settings.setOnAction(e -> {
            content_pane.getChildren().set(0, new Settings(cod_project));
        });

        vb_content.prefWidthProperty().bind(this.widthProperty());
        this.setContent(vb_content);
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

    public void setContentPane(Node view) {

        this.content_pane.getChildren().set(0, view);
    }

    public void update() {

        if(!this.content_pane.getChildren().isEmpty()) {

            try {
                this.content_pane.getChildren().set(0, this.content_pane.getChildren().get(0).getClass()
                        .getDeclaredConstructor(int.class).newInstance(this.cod_project));
    
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
    
                e.printStackTrace();
            }
        }
    }

    private String date(Timestamp date) {

        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd-MM-yyyy");
        return datetimeFormatter1.format(date); 
    }
}
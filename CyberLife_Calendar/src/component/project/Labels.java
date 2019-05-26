package component.project;

import java.util.ArrayList;

import component.projectFeatures.FeatureComponent;
import db.functions.projectFeatures.LoadFeature;
import db.pojo.LabelDB;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Labels extends VBox {

    private Button btn_add_label;
    private Label lbl_all_labels;
    private FlowPane flw_all_labels;
    private Label lbl_progress;
    private FlowPane flw_progress;

    private int cod_project;

    public Labels(int cod_project) {
        this.cod_project = cod_project;

        btn_add_label = new Button(" + adicionar marcador");
        btn_add_label.setOnAction(e -> {
            
            Stage stage = new Stage();
            stage.setScene(new display.scenes.Label(this.cod_project));
            stage.show();
        });

        lbl_all_labels = new Label("Todos os marcadores");

        LoadFeature feature = new LoadFeature();
        ArrayList<LabelDB> labels = feature.loadLabels(this.cod_project);

        flw_all_labels = new FlowPane();
        flw_all_labels.setHgap(10);
        flw_all_labels.setVgap(10);

        for (LabelDB label : labels) {
            
            FeatureComponent fc_label = new FeatureComponent(label);
            flw_all_labels.getChildren().add(fc_label);
        }

        lbl_progress = new Label("Progresso");
        flw_progress = new FlowPane();

        this.setSpacing(10);
        this.getChildren().addAll(btn_add_label, lbl_all_labels, flw_all_labels, lbl_progress, flw_progress);
    }
}
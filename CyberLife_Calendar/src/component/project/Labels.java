package component.project;

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
        flw_all_labels = new FlowPane();

        lbl_progress = new Label("Progresso");
        flw_progress = new FlowPane();

        this.getChildren().addAll(btn_add_label, lbl_all_labels, flw_all_labels, lbl_progress, flw_progress);
    }
}